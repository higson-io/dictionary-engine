package pl.decerto.hyperon.demo.dictionary.dict.impl;

import static java.util.stream.Collectors.toMap;
import static pl.decerto.hyperon.demo.dictionary.dict.impl.MultiValueDictionaryEntryFactory.createEntry;
import static pl.decerto.hyperon.demo.dictionary.dict.impl.MultiValueKeyNormalizer.normalize;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.smartparam.engine.core.output.MultiValue;

/**
 * Factory of multi-column dictionaries
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class MultiValueDictionaryFactory {

	public static MultiValueDictionary create(List<MultiValue> rows, Set<String> levels) {
		return MultiValueDictionary.builder()
				.levels(levels)
				.entries(createEntries(rows, levels))
				.build();
	}

	private static Map<String, MultiValueDictionaryEntry> createEntries(List<MultiValue> rows, Set<String> levels) {
		return rows.stream()
				.map(multiValue -> createEntry(levels, multiValue))
				.collect(toMap(entry -> normalize(entry.getKey()), Function.identity()));
	}
}
