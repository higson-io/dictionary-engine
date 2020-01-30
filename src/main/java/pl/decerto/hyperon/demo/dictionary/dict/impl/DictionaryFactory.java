package pl.decerto.hyperon.demo.dictionary.dict.impl;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.smartparam.engine.core.output.ParamValue;

import pl.decerto.hyperon.demo.dictionary.dict.Dictionary;

/**
 * Factory of dictionaries
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DictionaryFactory {

	public static Dictionary create(ParamValue paramValue) {
		return new MultiValueDictionaryConverter().convert(paramValue);
	}

	public static Dictionary create(ParamValue paramValue, String level) {
		return SingleValueDictionaryFactory.create(paramValue.rows(), level);
	}
}
