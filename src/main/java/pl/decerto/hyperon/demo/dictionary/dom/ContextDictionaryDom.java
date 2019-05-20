package pl.decerto.hyperon.demo.dictionary.dom;

import pl.decerto.hyperon.runtime.model.HyperonDomainObject;
import pl.decerto.hyperon.demo.dictionary.dict.ContextAwareDictionaryEntry;
import pl.decerto.hyperon.demo.dictionary.dict.InputLevelContext;
import pl.decerto.hyperon.demo.dictionary.dict.impl.ContextAwareDictionaryEntryFactory;

/**
 * Represents domain of dictionary with input and output levels
 */
public class ContextDictionaryDom extends AbstractDictionaryDom {

	ContextDictionaryDom(HyperonDomainObject domObj, InputLevelContext inputLevelContext) {
		super(domObj);
		with(inputLevelContext);
	}

	@Override
	protected DictionaryType getType() {
		return DictionaryType.CONTEXT_VALUES;
	}

	public ContextAwareDictionaryEntry getDictionaryEntry() {
		return ContextAwareDictionaryEntryFactory.create(getParamValue());
	}
}
