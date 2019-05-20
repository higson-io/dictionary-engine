package pl.decerto.hyperon.demo.dictionary.dom;

import org.smartparam.engine.core.output.ParamValue;
import pl.decerto.hyperon.runtime.model.HyperonDomainObject;

/**
 * Dictionary domain class
 */
abstract class AbstractDictionaryDom extends AbstractSimpleDom<AbstractDictionaryDom> {

	AbstractDictionaryDom(HyperonDomainObject domObj) {
		super(domObj);
	}

	protected abstract DictionaryType getType();

	protected ParamValue getParamValue() {
		return getAttrValue(getType().name());
	}
}
