package pl.decerto.hyperon.demo.dictionary.dict;

import pl.decerto.hyperon.runtime.core.HyperonContext;

/**
 * Represents Hyperon context containing values of parameter's input levels
 */
public class InputLevelContext extends HyperonContext {

	public InputLevelContext withInputLevel(String inputLevelName, Object value) {
		with(inputLevelName, value);
		return this;
	}
}
