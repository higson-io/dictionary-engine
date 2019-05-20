package pl.decerto.hyperon.demo.dictionary.service;

import pl.decerto.hyperon.demo.dictionary.dict.ContextAwareDictionaryEntry;
import pl.decerto.hyperon.demo.dictionary.dict.Dictionary;
import pl.decerto.hyperon.demo.dictionary.dict.DictionaryEntry;
import pl.decerto.hyperon.demo.dictionary.dict.InputLevelContext;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Api of dictionary service
 */
public interface DictionaryService {

	Dictionary getDictionaryByCode(String dictionaryCode);

	Dictionary getDictionaryLevel(String dictionaryCode, String level);

	Optional<DictionaryEntry> getDictionaryEntry(String dictionaryCode, String key);

	Optional<DictionaryEntry> getDictionaryEntry(String dictionaryCode, String key, String level);

	Set<String> getDictionaryLevels(String dictionaryCode);

	List<String> getAllDictionariesCodes();

	ContextAwareDictionaryEntry getDictionaryEntryForContext(String dictionaryCode, InputLevelContext ctx);
}
