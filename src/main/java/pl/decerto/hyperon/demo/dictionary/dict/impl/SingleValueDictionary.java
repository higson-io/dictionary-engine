package pl.decerto.hyperon.demo.dictionary.dict.impl;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.decerto.hyperon.demo.dictionary.dict.Dictionary;
import pl.decerto.hyperon.demo.dictionary.dict.DictionaryEntry;

/**
 * Class representing dictionary
 */
@EqualsAndHashCode
@RequiredArgsConstructor(staticName = "of")
@ToString
@Getter
final class SingleValueDictionary implements Dictionary {

	private final List<DictionaryEntry> entries;

	public Optional<DictionaryEntry> getEntry(final String key) {
		return this.entries.stream().filter(e -> equalsIgnoreCase(e.getKey(), key)).findFirst();
	}

	@Override
	public Set<String> getLevels() {
		return Collections.emptySet();
	}

	@Override
	public Optional<DictionaryEntry> getLevelEntry(String key, String level) {
		return Optional.empty();
	}
}
