package pl.decerto.hyperon.demo.dictionary.dict.impl;

import java.util.Optional;
import lombok.Value;
import pl.decerto.hyperon.demo.dictionary.dict.DictionaryEntry;

/**
 * Class representing entry of dictionary
 */
@Value(staticConstructor = "of")
class SingleValueDictionaryEntry implements DictionaryEntry {

	private final String key;
	private final String value;

	@Override
	public Optional<DictionaryEntry> getSubEntry(String level) {
		return Optional.empty();
	}
}
