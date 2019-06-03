package pl.decerto.hyperon.demo.dictionary.converter;

import io.hyperon.demos.dictionary_engine.*;
import lombok.experimental.UtilityClass;
import pl.decerto.hyperon.demo.dictionary.dict.ContextAwareDictionaryEntry;
import pl.decerto.hyperon.demo.dictionary.dict.DictionaryEntry;

@UtilityClass
public class EntryConverter {

	public GetEntryResponse toEntryResponse(DictionaryEntry entry) {
		var response = new GetEntryResponse();
		response.setKey(entry.getKey());
		response.setValue(entry.getValue());
		return response;
	}

	public GetContextAwareEntryResponse toContextAwareEntryResponse(ContextAwareDictionaryEntry entry) {
		if (entry == null) {
			return null;
		}
		var resultEntries = new EntriesType();
		entry.getEntries().forEach(dictionaryEntry -> {
			var resultEntry = new EntryType();
			resultEntry.setKey(dictionaryEntry.getKey());
			resultEntry.setValue(dictionaryEntry.getValue());
			resultEntries.getEntry().add(resultEntry);
		});

		var levels = new LevelsType();
		levels.getLevel().addAll(entry.getOutputLevels());

		var result = new GetContextAwareEntryResponse();
		result.setEntries(resultEntries);
		result.setOutputLevels(levels);
		return result;
	}
}
