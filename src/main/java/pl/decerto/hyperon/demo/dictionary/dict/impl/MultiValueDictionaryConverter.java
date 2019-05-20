package pl.decerto.hyperon.demo.dictionary.dict.impl;

import java.util.Set;
import org.smartparam.engine.core.output.ParamValue;
import pl.decerto.hyperon.demo.dictionary.dict.Dictionary;
import pl.decerto.hyperon.demo.dictionary.dom.SimpleConverter;

/**
 * Converter which creates dictionary with multiple values
 */
class MultiValueDictionaryConverter implements SimpleConverter<ParamValue, Dictionary> {

	@Override
	public MultiValueDictionary convert(ParamValue from) {
		Set<String> levels = DictionaryLevelsExtractor.extract(from);
		return MultiValueDictionaryFactory.create(from.rows(), levels);
	}
}
