package pl.decerto.hyperon.demo.dictionary.dict;

import lombok.NoArgsConstructor;
import pl.decerto.hyperon.runtime.core.HyperonContext;

import java.util.Map;

/**
 * Represents Hyperon context containing values of parameter's input levels
 */
@NoArgsConstructor
public class InputLevelContext extends HyperonContext {

	public InputLevelContext(Map<String, String> pathValueMap) {
		if (pathValueMap != null) {
			pathValueMap.forEach(this::withInputLevel);
		}
	}

	public InputLevelContext withInputLevel(String inputLevelName, Object value) {
		with(inputLevelName, value);
		return this;
	}
}
