package pl.decerto.hyperon.demo.dictionary.endpoint;

import lombok.Getter;

@Getter
public class EntryNotFoundException extends RuntimeException {

	private final String dictionaryCode;
	private final String key;
	private final String level;

	public EntryNotFoundException(String dictionaryCode, String key) {
		super(String.format("Dictionary : %s does not contain entry for key : %s", dictionaryCode, key));
		this.dictionaryCode = dictionaryCode;
		this.key = key;
		this.level = null;
	}

	public EntryNotFoundException(String dictionaryCode, String key, String level) {
		super(String.format("Dictionary : %s does not contain entry for key : %s and level %s",
				dictionaryCode, key, level));
		this.dictionaryCode = dictionaryCode;
		this.key = key;
		this.level = level;
	}
}
