package pl.decerto.hyperon.demo.dictionary.dict.impl;

import static pl.decerto.hyperon.demo.dictionary.dict.impl.MultiValueKeyNormalizer.normalize;
import static pl.decerto.hyperon.demo.dictionary.dict.impl.MultiValueKeyNormalizer.normalizeBCKey;

import java.util.Map;
import java.util.Optional;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import pl.decerto.hyperon.demo.dictionary.dict.DictionaryEntry;

/**
 * Entry of multi-column dictionary
 */
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
@Getter
@Builder
public class MultiValueDictionaryEntry implements DictionaryEntry {

	private final String key;
	private final Map<String, DictionaryEntry> values;

	@Override
	public String getValue() {
		return values.get(normalizeBCKey(key))
				.getValue();
	}

	@Override
	public String getValueByLevel(String level) {
		return values.get(level.toLowerCase()).getValue();
	}

	@Override
	public Optional<DictionaryEntry> getSubEntry(String level) {
		return Optional.ofNullable(values.get(normalize(level)));
	}
}
