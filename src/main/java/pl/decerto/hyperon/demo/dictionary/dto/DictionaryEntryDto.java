package pl.decerto.hyperon.demo.dictionary.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import pl.decerto.hyperon.demo.dictionary.dict.DictionaryEntry;

@Getter
@ApiModel("Single dictionary entry")
public class DictionaryEntryDto {

	@ApiModelProperty(value = "entry's key", example = "DEPOSIT_PRODUCT")
	private final String key;

	@ApiModelProperty(value = "entry's value", example = "Product where clients deposit their money in the bank")
	private final String value;

	public DictionaryEntryDto(DictionaryEntry dictionaryEntry) {
		key = dictionaryEntry.getKey();
		value = dictionaryEntry.getValue();
	}
}
