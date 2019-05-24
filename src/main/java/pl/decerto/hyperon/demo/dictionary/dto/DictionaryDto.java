package pl.decerto.hyperon.demo.dictionary.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import pl.decerto.hyperon.demo.dictionary.dict.Dictionary;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@ApiModel("Data of dictionary accessible by key")
public class DictionaryDto {

	@ApiModelProperty("dictionary entries, for multi-column dictionary contains entries from first data column")
	private final List<DictionaryEntryDto> entries;

	@ApiModelProperty(value = "available data columns (levels)")
	private final Set<String> levels;

	public DictionaryDto(Dictionary dictionary) {
		this.entries = dictionary.getEntries().stream()
				.map(DictionaryEntryDto::new)
				.collect(Collectors.toList());

		this.levels = dictionary.getLevels();
	}
}
