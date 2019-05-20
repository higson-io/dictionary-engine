package pl.decerto.hyperon.demo.dictionary.dict.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import pl.decerto.hyperon.demo.dictionary.dict.ContextAwareDictionaryEntry;
import pl.decerto.hyperon.demo.dictionary.dict.DictionaryEntry;

/**
 * Empty row of dictionary (for parameters, which can return empty values)
 */
class EmptyDictionaryEntry implements ContextAwareDictionaryEntry {

	@Override
	public String getValue(String outputLevelName) {
		return null;
	}

	@Override
	public Set<String> getOutputLevels() {
		return Collections.emptySet();
	}

	@Override
	public List<DictionaryEntry> getEntries() {
		return Collections.emptyList();
	}
}
