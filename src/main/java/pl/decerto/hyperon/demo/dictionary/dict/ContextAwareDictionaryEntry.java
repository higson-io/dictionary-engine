package pl.decerto.hyperon.demo.dictionary.dict;

import java.util.List;
import java.util.Set;

/**
 * Interface representing output levels of a dictionary row with many input levels
 */
public interface ContextAwareDictionaryEntry {

	String getValue(String outputLevelName);

	Set<String> getOutputLevels();

	List<DictionaryEntry> getEntries();
}
