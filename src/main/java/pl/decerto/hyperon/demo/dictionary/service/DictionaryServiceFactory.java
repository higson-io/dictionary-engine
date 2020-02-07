package pl.decerto.hyperon.demo.dictionary.service;

import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import pl.decerto.hyperon.demo.dictionary.dom.DictionariesDomRoot;
import pl.decerto.hyperon.demo.dictionary.dom.DictionariesDomRootImpl;
import pl.decerto.hyperon.demo.dictionary.engine.HyperonEngineWrapper;
import pl.decerto.hyperon.runtime.core.HyperonEngine;
import pl.decerto.hyperon.runtime.core.HyperonEngineFactory;
import pl.decerto.hyperon.runtime.sync.BaseWatcher;
import pl.decerto.hyperon.runtime.sync.DomainWatcherConfig;
import pl.decerto.hyperon.runtime.sync.WatcherConfig;

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

		HyperonEngineWrapper engineWrapper = new HyperonEngineWrapper(hyperonEngine, DICTIONARIES_PROFILE_NAME);
		DictionariesDomRoot dictionariesDomRoot = new DictionariesDomRootImpl(engineWrapper);
		return new DictionaryServiceImpl(dictionariesDomRoot);
	}

	private HyperonEngineFactory createHyperonEngineFactory(DataSource hyperonDataSource) {
		var engineFactory = new HyperonEngineFactory();
		engineFactory.setDataSource(hyperonDataSource);
		engineFactory.setAutoStartWatchers(false);
		engineFactory.setEnableDomainCache(true);

		return engineFactory;
	}

	public void destroy() {
		watchers.forEach(BaseWatcher::stop);
		factory.destroy();
	}

	private void startWatchers() {
		WatcherConfig watcherConf = getWatcherConf();
		DomainWatcherConfig domainWatcherConfig = getDomainWatcherConfig();
		watchers.add(factory.startParamWatcher(factory.getParamWatcherConfig()));
		watchers.add(factory.startFunctionWatcher(factory.getFunctionWatcherConfig()));
		watchers.add(factory.startDomainWatcher(domainWatcherConfig));
		watchers.add(factory.startVersionWatcher(watcherConf));
		watchers.add(factory.startScheduleWatcher());
		watchers.add(factory.startRefreshWatcher(factory.getRefreshWatcherConfig()));
	}

	private WatcherConfig getWatcherConf() {
		return new WatcherConfig(factory.getWatcherStartDelaySeconds(), factory.getWatcherPauseSeconds(),
			factory.getWatcherErrorPauseSeconds());
	}

	private DomainWatcherConfig getDomainWatcherConfig() {
		return new DomainWatcherConfig(factory.getWatcherStartDelaySeconds(), factory.getWatcherPauseSeconds(),
			factory.getWatcherErrorPauseSeconds());
	}

}
