package pl.decerto.hyperon.demo.dictionary.dict.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.smartparam.engine.core.output.ParamValue;
import org.smartparam.engine.core.parameter.Level;
import pl.decerto.hyperon.runtime.model.MpParameter;
import pl.decerto.hyperon.demo.dictionary.dict.ContextAwareDictionaryEntry;
import pl.decerto.hyperon.demo.dictionary.dict.DictionaryEntry;

/**
 * Entry of dictionary with input levels (containing only output levels)
 */
class OutputLevelDictionaryEntry implements ContextAwareDictionaryEntry {

	private final ParamValue paramValue;

	private final Set<String> outputLevels;

	private final List<DictionaryEntry> entries;

	OutputLevelDictionaryEntry(ParamValue paramValue) {
		this.paramValue = paramValue;
		this.outputLevels = initOutputLevels(paramValue);
		this.entries = initEntries(outputLevels, paramValue);
	}

	private List<DictionaryEntry> initEntries(Set<String> outputLevels, ParamValue paramValue) {
		return outputLevels.stream()
				.map(col -> SingleValueDictionaryEntry.of(col, paramValue.getString(col)))
				.collect(Collectors.toList());
	}

	@Override
	public String getValue(String outputLevelName) {
		return paramValue.getString(outputLevelName);
	}

	@Override
	public Set<String> getOutputLevels() {
		return outputLevels;
	}

	@Override
	public List<DictionaryEntry> getEntries() {
		return entries;
	}

	private Set<String> initOutputLevels(ParamValue paramValue) {
		MpParameter metadata = (MpParameter) paramValue.getMetadata();
		return metadata.getLevels().stream()
				.skip(metadata.getInputLevels())
				.map(Level::getName)
				.collect(Collectors.toSet());
	}
}
