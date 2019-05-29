package pl.decerto.hyperon.demo.dictionary.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.decerto.hyperon.demo.dictionary.dto.ControllerResponseDto;

import static pl.decerto.hyperon.demo.dictionary.configuration.ErrorHandlingConfiguration.NOT_FOUND_EXCEPTIONS;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(HttpMessageConversionException.class)
	protected ResponseEntity<ControllerResponseDto<Void>> handleHttpMessageConversionProblem(
			HttpMessageConversionException ex) {

		return ControllerResponseFactory.error(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ControllerResponseDto<Void>> handleGeneralException(Exception ex) {

		for (Class<? extends Exception> excClass: NOT_FOUND_EXCEPTIONS) {
			if (excClass.isInstance(ex)) {
				return ControllerResponseFactory.error(HttpStatus.NOT_FOUND, ex.getMessage());
			}
		}
		return ControllerResponseFactory.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
	}

}
