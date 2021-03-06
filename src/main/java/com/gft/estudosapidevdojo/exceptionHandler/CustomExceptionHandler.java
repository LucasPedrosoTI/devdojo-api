package com.gft.estudosapidevdojo.exceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.security.sasl.AuthenticationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String userMessage = this.messageSource.getMessage("message.invalid", null, LocaleContextHolder.getLocale());
		String devMessage = ExceptionUtils.getMessage(ex);

		List<Error> erros = Arrays
				.asList(Error.builder().userMessage(userMessage).developerMessage(devMessage).build());

		return this.handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Error> erros = this.createErrorList(ex.getBindingResult());

		return this.handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ EmptyResultDataAccessException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
			WebRequest request) {
		String userMessage = this.messageSource.getMessage("resource.not-found", null, LocaleContextHolder.getLocale());
		String devMessage = ex.toString();

		List<Error> erros = Arrays
				.asList(Error.builder().developerMessage(devMessage).userMessage(userMessage).build());

		return this.handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
			WebRequest request) {
		String userMessage = this.messageSource.getMessage("resource.operation-not-allowed", null,
				LocaleContextHolder.getLocale());
		String devMessage = ExceptionUtils.getRootCauseMessage(ex);

		List<Error> erros = Arrays
				.asList(Error.builder().developerMessage(devMessage).userMessage(userMessage).build());

		return this.handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ InvalidDataAccessApiUsageException.class })
	public ResponseEntity<Object> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex,
			WebRequest request) {
		String userMessage = this.messageSource.getMessage("resource.operation-not-allowed", null,
				LocaleContextHolder.getLocale());
		String devMessage = ExceptionUtils.getRootCauseMessage(ex);

		List<Error> erros = Arrays
				.asList(Error.builder().developerMessage(devMessage).userMessage(userMessage).build());

		return this.handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

//	@ExceptionHandler({ BadCredentialsException.class })
//	public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
//
//		String  userMessage = this.messageSource.getMessage("recurso.acesso-negado", null,
//				LocaleContextHolder.getLocale());
//		String devMessage = ExceptionUtils.getRootCauseMessage(ex);
//
//		List<Erro> erros = Arrays.asList(new Erro( userMessage, devMessage));
//
//		return this.handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
//	}

	@ExceptionHandler({ AuthenticationException.class })
	public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, WebRequest request) {

		String userMessage = ex.getMessage();
		String devMessage = ExceptionUtils.getRootCauseMessage(ex);

		List<Error> erros = Arrays
				.asList(Error.builder().developerMessage(devMessage).userMessage(userMessage).build());

		return this.handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
	}

	private List<Error> createErrorList(BindingResult bindingResult) {
		List<Error> erros = new ArrayList<>();

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String userMessage = this.messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String devMessage = fieldError.toString();

			erros.add(Error.builder().developerMessage(devMessage).userMessage(userMessage).build());
		}

		return erros;
	}

}
