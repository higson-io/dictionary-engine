package pl.decerto.hyperon.demo.dictionary.dict.impl;

import static pl.decerto.hyperon.demo.dictionary.dict.impl.MultiValueKeyNormalizer.normalize;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.decerto.hyperon.demo.dictionary.dict.Dictionary;
import pl.decerto.hyperon.demo.dictionary.dict.DictionaryEntry;

/**
 * Class of multi-column dictionary
 */
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Builder
class MultiValueDictionary implements Dictionary {

	private final Set<String> levels;
	private final Map<String, MultiValueDictionaryEntry> entries;

	@Override
	public Optional<DictionaryEntry> getEntry(String key) {
		return Optional.ofNullable(entries.get(normalize(key)));
	}

	@Override
	public Optional<DictionaryEntry> getLevelEntry(String key, String level) {
		return Optional.ofNullable(entries.get(normalize(key)))
				.flatMap(entry -> entry.getSubEntry(level));
	}

	@Override
	public List<DictionaryEntry> getEntries() {
		return new ArrayList<>(entries.values());
	}
}
