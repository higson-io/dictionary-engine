package pl.decerto.hyperon.demo.dictionary.dict.impl;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.smartparam.engine.core.output.MultiValue;
import org.smartparam.engine.core.output.ParamValue;
import pl.decerto.hyperon.demo.dictionary.dict.Dictionary;
import pl.decerto.hyperon.demo.dictionary.dom.SimpleConverter;

/**
 * Factory of dictionaries
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DictionaryFactory {

	private static final int SINGLE_VALUE_DICTIONARY_MV_SIZE = 2;

	private static final SimpleConverter<ParamValue, Dictionary> SIMPLE_DICTIONARY_CONVERTER = new SingleValueDictionaryConverter();
	private static final SimpleConverter<ParamValue, Dictionary> MULTI_VALUE_DICTIONARY_CONVERTER = new MultiValueDictionaryConverter();

	public static Dictionary create(ParamValue paramValue) {
		return getConverter(paramValue).convert(paramValue);
	}

	private static SimpleConverter<ParamValue, Dictionary> getConverter(ParamValue paramValue) {
		return paramValue.rows().stream()
				.findFirst()
				.map(MultiValue::size)
				.filter(size -> size > SINGLE_VALUE_DICTIONARY_MV_SIZE)
				.map(unused -> MULTI_VALUE_DICTIONARY_CONVERTER)
				.orElse(SIMPLE_DICTIONARY_CONVERTER);
	}

	public static Dictionary create(ParamValue paramValue, String level) {
		return SingleValueDictionaryFactory.create(paramValue.rows(), level);
	}
}
