package pl.decerto.hyperon.demo.dictionary.dict;

import java.util.Optional;

/**
 * Interface of dictionary entry
 */
public interface DictionaryEntry {

	String getKey();

	String getValue();

	String getValueByLevel(String level);

	Optional<DictionaryEntry> getSubEntry(String level);
}
