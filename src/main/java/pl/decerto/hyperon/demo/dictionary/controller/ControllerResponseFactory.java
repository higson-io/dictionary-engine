package pl.decerto.hyperon.demo.dictionary.controller;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.decerto.hyperon.demo.dictionary.dto.ControllerResponseDto;

import java.util.Optional;

@UtilityClass
public class ControllerResponseFactory {

	public static <T> ResponseEntity<ControllerResponseDto<T>> response(T data) {
		return response(data, null);
	}

	public static <T> ResponseEntity<ControllerResponseDto<T>> response(Optional<T> data, String notFoundMessage) {
		return response(data.orElse(null), notFoundMessage);
	}

	public static <T> ResponseEntity<ControllerResponseDto<T>> response(T data, String notFoundMessage) {
		if (data == null) {
			return new ResponseEntity<>(ControllerResponseDto.error(notFoundMessage), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(ControllerResponseDto.okResponse(data));
	}

	public static <T> ResponseEntity<ControllerResponseDto<T>> error(HttpStatus status, String errorMessage) {
		return new ResponseEntity<>(ControllerResponseDto.error(errorMessage), status);
	}

}
