package pl.decerto.hyperon.demo.dictionary.dto;

import java.util.Set;

import lombok.Getter;

import io.swagger.annotations.ApiModelProperty;

@Getter
abstract class DictionaryDto {

	@ApiModelProperty(value = "available data columns (levels)",
		example = "['DESCRIPTION', 'TYPE', 'PRODUCT_CODE']",
		reference = "Set",
		required = true)
	private final Set<String> levels;

	DictionaryDto(Set<String> levels) {
		this.levels = levels;
	}
}
