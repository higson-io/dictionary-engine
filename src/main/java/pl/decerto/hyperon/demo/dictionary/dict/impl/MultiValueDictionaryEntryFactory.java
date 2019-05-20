package pl.decerto.hyperon.demo.dictionary.dict.impl;

import static java.util.stream.Collectors.toMap;
import static pl.decerto.hyperon.demo.dictionary.dict.impl.MultiValueKeyNormalizer.normalize;
import static pl.decerto.hyperon.demo.dictionary.dict.impl.MultiValueKeyNormalizer.normalizeBCKey;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.smartparam.engine.core.output.MultiValue;
import pl.decerto.hyperon.demo.dictionary.dict.DictionaryEntry;

/**
 * Factory of multi-column dictionary entries
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class MultiValueDictionaryEntryFactory {

	private static final int CODE_POSITION = 0;

	static MultiValueDictionaryEntry createEntry(Set<String> levels, MultiValue multiValue) {
		String code = multiValue.getString(CODE_POSITION);
		Map<String, DictionaryEntry> mvEntries = levels.stream()
				.map(level -> SingleValueDictionaryEntry.of(level, multiValue.getString(level)))
				.collect(toMap(entry -> normalize(entry.getKey()), Function.identity()));

		DictionaryEntry backwardsCompatibilityEntry = SingleValueDictionaryFactory.ENTRY_CONVERTER.convert(multiValue);
		mvEntries.put(normalizeBCKey(backwardsCompatibilityEntry.getKey()), backwardsCompatibilityEntry);
		return MultiValueDictionaryEntry.builder()
				.key(code)
				.values(mvEntries)
				.build();
	}
}
