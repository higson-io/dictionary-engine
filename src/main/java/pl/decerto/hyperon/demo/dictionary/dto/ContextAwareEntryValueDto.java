package pl.decerto.hyperon.demo.dictionary.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ApiModel("Value of data column (level) retrieved from context-aware dictionary")
public class ContextAwareEntryValueDto {

	@ApiModelProperty("value read from context-aware dictionary")
	private final String value;

}
