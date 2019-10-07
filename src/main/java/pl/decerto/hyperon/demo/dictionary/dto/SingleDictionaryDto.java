package pl.decerto.hyperon.demo.dictionary.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

import io.swagger.annotations.ApiModel;
import pl.decerto.hyperon.demo.dictionary.dict.Dictionary;

@Getter
@ApiModel("Data of dictionary accessible by code and level")
public class SingleDictionaryDto extends DictionaryDto {

	private final List<DictionaryEntryDto> entries;

	public SingleDictionaryDto(Dictionary dictionary) {
		super(dictionary.getLevels());
		this.entries = dictionary.getEntries().stream()
			.map(DictionaryEntryDto::new)
			.collect(Collectors.toList());
	}
}
