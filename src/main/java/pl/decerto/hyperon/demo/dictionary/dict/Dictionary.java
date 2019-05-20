package pl.decerto.hyperon.demo.dictionary.dict;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Interface representing dictionaries
 */
public interface Dictionary {

	List<DictionaryEntry> getEntries();

	Optional<DictionaryEntry> getEntry(String key);

	Set<String> getLevels();

	Optional<DictionaryEntry> getLevelEntry(String key, String level);
}
