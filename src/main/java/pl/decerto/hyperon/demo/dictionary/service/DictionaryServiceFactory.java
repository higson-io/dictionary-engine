package pl.decerto.hyperon.demo.dictionary.service;

import java.util.HashSet;
import java.util.Set;
import javax.sql.DataSource;
import pl.decerto.hyperon.runtime.core.HyperonEngine;
import pl.decerto.hyperon.runtime.core.HyperonEngineFactory;
import pl.decerto.hyperon.runtime.sync.BaseWatcher;
import pl.decerto.hyperon.runtime.sync.WatcherConfig;
import pl.decerto.hyperon.demo.dictionary.dom.DictionariesDomRoot;
import pl.decerto.hyperon.demo.dictionary.dom.DictionariesDomRootImpl;
import pl.decerto.hyperon.demo.dictionary.engine.HyperonEngineWrapper;

/**
 * Configuration class, creating dictionary service
 */
public class DictionaryServiceFactory {

	static final String DICTIONARIES_PROFILE_NAME = "DICTIONARIES";

	private final HyperonEngineFactory factory;

	private final Set<BaseWatcher> watchers = new HashSet<>();

	public DictionaryServiceFactory(DataSource hyperonDataSource) {
		this.factory = createHyperonEngineFactory(hyperonDataSource);
	}

	public DictionaryService create() {
		if (factory.isAutoStartWatchers()) {
			throw new IllegalArgumentException("Auto start cannot be used with this factory!");
		}

		HyperonEngine hyperonEngine = factory.create();
		startWatchers();

		HyperonEngineWrapper eagentEngine = new HyperonEngineWrapper(hyperonEngine, DICTIONARIES_PROFILE_NAME);
		DictionariesDomRoot dictionariesDomRoot = new DictionariesDomRootImpl(eagentEngine);
		return new DictionaryServiceImpl(dictionariesDomRoot);
	}

	private HyperonEngineFactory createHyperonEngineFactory(DataSource hyperonDataSource) {
		HyperonEngineFactory factory = new HyperonEngineFactory();
		factory.setDataSource(hyperonDataSource);
		factory.setEnableDomainCache(true);

		return factory;
	}

	public void destroy() {
		watchers.forEach(BaseWatcher::stop);
		factory.destroy();
	}

	private void startWatchers() {
		WatcherConfig watcherConf = new WatcherConfig(factory.getWatcherStartDelaySeconds(), factory.getWatcherPauseSeconds(), factory.getWatcherErrorPauseSeconds());
		watchers.add(factory.startParamWatcher(factory.getParamWatcherConfig()));
		watchers.add(factory.startFunctionWatcher(factory.getFunctionWatcherConfig()));
		watchers.add(factory.startDomainWatcher(watcherConf));
		watchers.add(factory.startVersionWatcher(watcherConf));
		watchers.add(factory.startScheduleWatcher());
		watchers.add(factory.startRefreshWatcher(factory.getRefreshWatcherConfig()));
	}

}
