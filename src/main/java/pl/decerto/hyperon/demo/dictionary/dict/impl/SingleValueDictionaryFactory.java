package pl.decerto.hyperon.demo.dictionary.dict.impl;

import static java.util.stream.Collectors.toList;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.smartparam.engine.core.output.MultiValue;

import pl.decerto.hyperon.demo.dictionary.dict.DictionaryEntry;
import pl.decerto.hyperon.demo.dictionary.dom.SimpleConverter;

/**
 * Factory of single-column dictionaries
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class SingleValueDictionaryFactory {

	private static final int KEY_POSITION = 0;
	private static final int VALUE_POSITION = 1;

	static final SimpleConverter<MultiValue, DictionaryEntry> ENTRY_CONVERTER = mv ->
			SingleValueDictionaryEntry.of(mv.getString(KEY_POSITION), mv.getString(VALUE_POSITION));

	static SingleValueDictionary create(List<MultiValue> rows, String level) {
		List<DictionaryEntry> entries = rows.stream()
				.map(multiValue -> SingleValueDictionaryEntry.of(multiValue.getString(KEY_POSITION), multiValue.getString(level.toUpperCase())))
				.collect(toList());
		return SingleValueDictionary.of(entries);
	}
}
