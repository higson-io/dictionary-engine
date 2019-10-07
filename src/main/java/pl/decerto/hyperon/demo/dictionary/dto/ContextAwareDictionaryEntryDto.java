package pl.decerto.hyperon.demo.dictionary.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import pl.decerto.hyperon.demo.dictionary.dict.ContextAwareDictionaryEntry;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@ApiModel("Entry of context-aware dictionary")
public class ContextAwareDictionaryEntryDto {

	@ApiModelProperty(value = "codes of available data columns (levels)",
		example = "['DESCRIPTION', 'TYPE', 'PRODUCT_CODE']",
		reference = "Set",
		required = true)
	private final Set<String> outputLevels;

	@ApiModelProperty(value = "entries corresponding to data columns (levels)",
			notes = "key of each entry is the code of corresponding output level")
	private final List<DictionaryEntryDto> entries;

	public ContextAwareDictionaryEntryDto(ContextAwareDictionaryEntry entry) {
		this.outputLevels = entry.getOutputLevels();
		this.entries = entry.getEntries().stream()
				.map(DictionaryEntryDto::new)
				.collect(Collectors.toList());
	}
}
