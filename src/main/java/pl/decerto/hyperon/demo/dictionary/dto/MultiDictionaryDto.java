package pl.decerto.hyperon.demo.dictionary.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import pl.decerto.hyperon.demo.dictionary.dict.Dictionary;
import pl.decerto.hyperon.demo.dictionary.dict.DictionaryEntry;
import pl.decerto.hyperon.demo.dictionary.dict.impl.MultiValueDictionaryEntry;

@Getter
@ApiModel("Data of dictionary accessible by code")
public class MultiDictionaryDto extends DictionaryDto {

	@ApiModelProperty(value = "dictionary entries, for multi-column dictionary contains all entries",
		example =   "{'DESCRIPTION': 'Credit card', 'TYPE' : 'CREDIT_PRODUCT', 'PRODUCT_CODE' : 'CARD'}",
		dataType = "Map[String,String]",
		reference = "Map",
		required = true)
	private final List<Map<String, String>> entries;

	public MultiDictionaryDto(Dictionary dictionary) {
		super(dictionary.getLevels());
		this.entries = dictionary.getEntries().stream()
			.map((DictionaryEntry dictionaryEntry) -> createEntry(dictionaryEntry, dictionary.getLevels()))
			.collect(Collectors.toList());
	}

	private Map<String, String> createEntry(DictionaryEntry dictionaryEntry, Set<String> levels) {
		Map<String, String> entry = new HashMap<>();

		if (dictionaryEntry instanceof MultiValueDictionaryEntry) {
			for (String level : levels) {
				entry.put(level, (dictionaryEntry).getValueByLevel(level));
			}
		}

		return entry;
	}
}
