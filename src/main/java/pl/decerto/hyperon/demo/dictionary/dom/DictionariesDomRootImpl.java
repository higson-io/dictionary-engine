package pl.decerto.hyperon.demo.dictionary.dom;

import java.util.List;
import pl.decerto.hyperon.runtime.model.HyperonDomainObject;
import pl.decerto.hyperon.demo.dictionary.dict.InputLevelContext;
import pl.decerto.hyperon.demo.dictionary.engine.HyperonEngineWrapper;

/**
 * Class representing domain of dictionaries
 */
public class DictionariesDomRootImpl implements DictionariesDomRoot {

	private static final SimpleConverter<HyperonDomainObject, String> MP_DOMAIN_OBJECT_TO_CODE_CONVERTER = HyperonDomainObject::getCode;

	private final HyperonEngineWrapper engineWrapper;

	public DictionariesDomRootImpl(HyperonEngineWrapper engineWrapper) {
		this.engineWrapper = engineWrapper;
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
		HyperonDomainObject domainObject = engineWrapper.getDomainObj(Domains.ROOT.getPath());
		return MP_DOMAIN_OBJECT_TO_CODE_CONVERTER.convertAll(domainObject.getChildren(Domains.DICTIONARIES.getPath()));
	}

	private HyperonDomainObject getDictionaryDomain(String dictionaryCode) {
		return engineWrapper.getDomainObj(Domains.DICTIONARY.getPath(), dictionaryCode);
	}
}
