package pl.decerto.hyperon.demo.dictionary.dom;

import java.util.Set;
import pl.decerto.hyperon.runtime.model.HyperonDomainObject;
import pl.decerto.hyperon.demo.dictionary.dict.Dictionary;
import pl.decerto.hyperon.demo.dictionary.dict.impl.DictionaryFactory;
import pl.decerto.hyperon.demo.dictionary.dict.impl.DictionaryLevelsExtractor;

/**
 * Represents domain of dictionary with output levels
 */
public class DictionaryDom extends AbstractDictionaryDom {

	DictionaryDom(HyperonDomainObject domObj) {
		super(domObj);
	}

	@Override
	protected DictionaryType getType() {
		return DictionaryType.VALUES;
	}

	public Dictionary getDictionary() {
		return DictionaryFactory.create(getParamValue());
	}

	public Set<String> getDictionaryLevels() {
		return DictionaryLevelsExtractor.extract(getParamValue());
	}

	public Dictionary getDictionary(String level) {
		return DictionaryFactory.create(getParamValue(), level);
	}
}
