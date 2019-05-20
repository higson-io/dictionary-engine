package pl.decerto.hyperon.demo.dictionary.dict.impl;

import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.smartparam.engine.core.output.ParamValue;
import pl.decerto.hyperon.demo.dictionary.dict.ContextAwareDictionaryEntry;

/**
 * Factory of dictionary entries with input levels
 */
@UtilityClass
public class ContextAwareDictionaryEntryFactory {

	public static ContextAwareDictionaryEntry create(ParamValue paramValue) {
		if (isEmpty(paramValue)) {
			return new EmptyDictionaryEntry();
		}
		return new OutputLevelDictionaryEntry(paramValue);
	}

	private static boolean isEmpty(ParamValue paramValue) {
		return Objects.isNull(paramValue) || paramValue.size() == 0;
	}
}
