package pl.decerto.hyperon.demo.dictionary.dict.impl;

import static java.util.stream.Collectors.toSet;
import java.util.Set;
import lombok.experimental.UtilityClass;
import org.smartparam.engine.core.output.ParamValue;
import org.smartparam.engine.core.parameter.Level;
import pl.decerto.hyperon.runtime.model.MpParameter;

/**
 * Class extracting levels of dictionary from ParamValue
 */
@UtilityClass
public class DictionaryLevelsExtractor {

	public static Set<String> extract(ParamValue paramValue) {
		MpParameter metadata = (MpParameter) paramValue.getMetadata();
		return metadata.getLevels().stream()
				.map(Level::getName)
				.collect(toSet());
	}
}
