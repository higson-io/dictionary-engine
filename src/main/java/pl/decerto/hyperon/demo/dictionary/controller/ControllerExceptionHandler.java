package pl.decerto.hyperon.demo.dictionary.controller;

import org.smartparam.engine.core.ParameterValueNotFoundException;
import org.smartparam.engine.core.output.UnknownLevelNameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.decerto.hyperon.demo.dictionary.dom.AttributeNotFoundException;
import pl.decerto.hyperon.demo.dictionary.dto.ControllerResponseDto;
import pl.decerto.hyperon.demo.dictionary.engine.IncorrectDomainPathException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(IncorrectDomainPathException.class)
	protected ResponseEntity<ControllerResponseDto<Void>> handleIncorrectDomainPath(IncorrectDomainPathException ex) {

		return handleExceptionForNotFound(ex);
	}

	@ExceptionHandler(AttributeNotFoundException.class)
	protected ResponseEntity<ControllerResponseDto<Void>> handleAttributeNotFound(AttributeNotFoundException ex) {

		return handleExceptionForNotFound(ex);
	}

	@ExceptionHandler(UnknownLevelNameException.class)
	protected ResponseEntity<ControllerResponseDto<Void>> handleUnknownLevelName(UnknownLevelNameException ex) {

		return handleExceptionForNotFound(ex);
	}

	@ExceptionHandler(ParameterValueNotFoundException.class)
	protected ResponseEntity<ControllerResponseDto<Void>> handleParameterValueNotFound(
			ParameterValueNotFoundException ex) {

		return handleExceptionForNotFound(ex);
	}

	@ExceptionHandler(HttpMessageConversionException.class)
	protected ResponseEntity<ControllerResponseDto<Void>> handleHttpMessageConversionProblem(
			HttpMessageConversionException ex) {

		return ControllerResponseFactory.error(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ControllerResponseDto<Void>> handleGeneralException(Exception ex) {

		return ControllerResponseFactory.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
	}

	private ResponseEntity<ControllerResponseDto<Void>> handleExceptionForNotFound(Exception ex) {
		return ControllerResponseFactory.error(HttpStatus.NOT_FOUND, ex.getMessage());
	}

}
