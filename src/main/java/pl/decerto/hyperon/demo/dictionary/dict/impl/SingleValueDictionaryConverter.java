package pl.decerto.hyperon.demo.dictionary.dict.impl;

import org.smartparam.engine.core.output.ParamValue;
import pl.decerto.hyperon.demo.dictionary.dict.Dictionary;
import pl.decerto.hyperon.demo.dictionary.dom.SimpleConverter;

/**
 * Converter creating dictionary with single value
 */
class SingleValueDictionaryConverter implements SimpleConverter<ParamValue, Dictionary> {

	@Override
	public SingleValueDictionary convert(ParamValue from) {
		return SingleValueDictionaryFactory.create(from.rows());
	}
}
