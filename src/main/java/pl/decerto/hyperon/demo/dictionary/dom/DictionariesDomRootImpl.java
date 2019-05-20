package pl.decerto.hyperon.demo.dictionary.dom;

import java.util.List;
import pl.decerto.hyperon.runtime.model.HyperonDomainObject;
import pl.decerto.hyperon.demo.dictionary.dict.InputLevelContext;
import pl.decerto.hyperon.demo.dictionary.engine.HyperonEngineWrapper;

/**
 * Klasa reprezentująca domene słowników
 */
public class DictionariesDomRootImpl implements DictionariesDomRoot {

	private static final SimpleConverter<HyperonDomainObject, String> MP_DOMAIN_OBJECT_TO_CODE_CONVERTER = HyperonDomainObject::getCode;

	private final HyperonEngineWrapper eagentEngine;

	public DictionariesDomRootImpl(HyperonEngineWrapper eagentEngine) {
		this.eagentEngine = eagentEngine;
	}

	@Override
	public DictionaryDom dictionaryDom(String dictionaryCode) {
		return new DictionaryDom(getDictionaryDomain(dictionaryCode));
	}

	@Override
	public ContextDictionaryDom contextDictionaryDom(String dictionaryCode, InputLevelContext ctx) {
		return new ContextDictionaryDom(getDictionaryDomain(dictionaryCode), ctx);
	}

	@Override
	public List<String> dictionariesCodes() {
		HyperonDomainObject domainObject = eagentEngine.getDomainObj(Domains.ROOT.getPath());
		return MP_DOMAIN_OBJECT_TO_CODE_CONVERTER.convertAll(domainObject.getChildren(Domains.DICTIONARIES.getPath()));
	}

	private HyperonDomainObject getDictionaryDomain(String dictionaryCode) {
		return eagentEngine.getDomainObj(Domains.DICTIONARY.getPath(), dictionaryCode);
	}
}
