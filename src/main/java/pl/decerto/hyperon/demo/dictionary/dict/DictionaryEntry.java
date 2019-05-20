package pl.decerto.hyperon.demo.dictionary.dict;

import java.util.Optional;

/**
 * Interface of dictionary entry
 */
public interface DictionaryEntry {

	String getKey();

	String getValue();

	Optional<DictionaryEntry> getSubEntry(String level);
}
