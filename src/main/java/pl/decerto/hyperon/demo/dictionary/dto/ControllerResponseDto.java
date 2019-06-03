package pl.decerto.hyperon.demo.dictionary.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel(description = "Wrapper for controller response")
public class ControllerResponseDto<T> {

	@ApiModelProperty("response data, empty in case of error")
	private final T data;

	@ApiModelProperty("error details, empty for successful requests")
	private final ControllerErrorDto error;

	private ControllerResponseDto(T data, ControllerErrorDto error) {
		this.data = data;
		this.error = error;
	}

	public static <U> ControllerResponseDto<U> okResponse(U data) {
		return new ControllerResponseDto<>(data, null);
	}

	public static <U> ControllerResponseDto<U> error(String errorMessage) {
		return new ControllerResponseDto<>(null, new ControllerErrorDto(errorMessage));
	}
}
