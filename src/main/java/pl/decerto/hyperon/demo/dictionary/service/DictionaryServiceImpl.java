package pl.decerto.hyperon.demo.dictionary.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import pl.decerto.hyperon.demo.dictionary.dom.ContextDictionaryDom;
import pl.decerto.hyperon.demo.dictionary.dom.DictionariesDomRoot;
import pl.decerto.hyperon.demo.dictionary.dom.DictionaryDom;
import pl.decerto.hyperon.demo.dictionary.dict.ContextAwareDictionaryEntry;
import pl.decerto.hyperon.demo.dictionary.dict.Dictionary;
import pl.decerto.hyperon.demo.dictionary.dict.DictionaryEntry;
import pl.decerto.hyperon.demo.dictionary.dict.InputLevelContext;

/**
 * Implementation of dictionary service api
 */
class DictionaryServiceImpl implements DictionaryService {

	private final DictionariesDomRoot dictionariesDomRoot;

	DictionaryServiceImpl(DictionariesDomRoot dictionariesDomRoot) {
		this.dictionariesDomRoot = dictionariesDomRoot;
	}

	@Override
	public Dictionary getDictionaryByCode(String dictionaryCode) {
		return getDictionary(dictionaryCode).getDictionary();
	}

	@Override
	public Dictionary getDictionaryLevel(String dictionaryCode, String level) {
		return getDictionary(dictionaryCode).getDictionary(level);
	}

	@Override
	public Optional<DictionaryEntry> getDictionaryEntry(String dictionaryCode, String key) {
		return getDictionaryByCode(dictionaryCode).getEntry(key);
	}

	@Override
	public Optional<DictionaryEntry> getDictionaryEntry(String dictionaryCode, String key, String level) {
		return getDictionaryByCode(dictionaryCode).getLevelEntry(key, level);
	}

	@Override
	public Set<String> getDictionaryLevels(String dictionaryCode) {
		return getDictionary(dictionaryCode).getDictionaryLevels();
	}

	@Override
	public List<String> getAllDictionariesCodes() {
		return dictionariesDomRoot.dictionariesCodes();
	}

	@Override
	public ContextAwareDictionaryEntry getDictionaryEntryForContext(String dictionaryCode, InputLevelContext ctx) {
		return getContextDictionary(dictionaryCode, ctx)
				.getDictionaryEntry();
	}

	private ContextDictionaryDom getContextDictionary(String dictionaryCode, InputLevelContext ctx) {
		return dictionariesDomRoot.contextDictionaryDom(dictionaryCode, ctx);
	}

	private DictionaryDom getDictionary(String dictionaryCode) {
		return dictionariesDomRoot.dictionaryDom(dictionaryCode);
	}
}
