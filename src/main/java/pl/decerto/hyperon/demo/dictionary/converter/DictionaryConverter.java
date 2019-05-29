package pl.decerto.hyperon.demo.dictionary.converter;

import io.hyperon.demos.dictionary_engine.EntriesType;
import io.hyperon.demos.dictionary_engine.EntryType;
import io.hyperon.demos.dictionary_engine.GetDictionaryResponse;
import io.hyperon.demos.dictionary_engine.LevelsType;
import lombok.experimental.UtilityClass;
import pl.decerto.hyperon.demo.dictionary.dict.Dictionary;

@UtilityClass
public class DictionaryConverter {

	public static GetDictionaryResponse toDictionaryResponse(Dictionary dictionary) {
		if (dictionary == null) {
			return null;
		}

		var entries = new EntriesType();
		var entriesList = entries.getEntry();
		dictionary.getEntries().forEach(dictionaryEntry -> {
			var responseEntry = new EntryType();
			responseEntry.setKey(dictionaryEntry.getKey());
			responseEntry.setValue(dictionaryEntry.getValue());
			entriesList.add(responseEntry);
		});

		var response = new GetDictionaryResponse();
		response.setEntries(entries);

		var levels = new LevelsType();
		levels.getLevel().addAll(dictionary.getLevels());
		response.setLevels(levels);

		return response;
	}
}
