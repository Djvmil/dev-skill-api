package com.jcdecaux.devskill.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {


	@ExceptionHandler(DevSkillException.class)
	public final ResponseEntity<ApiError> devSkillException(DevSkillException ex) {

		return new ResponseEntity<>(new ApiError(
				HttpStatus.BAD_REQUEST,
				ex.getLocalizedMessage(),
				ex.getLocalizedMessage()
		), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotFoundException.class)
	public final ResponseEntity<ApiError> notFoundException(NotFoundException ex) {

		return new ResponseEntity<>(new ApiError(
				HttpStatus.BAD_REQUEST,
				ex.getLocalizedMessage(),
				ex.getLocalizedMessage()
		), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DuplicateException.class)
	public final ResponseEntity<ApiError> duplicateException(DuplicateException ex) {
		return new ResponseEntity<>(new ApiError(
				HttpStatus.BAD_REQUEST,
				ex.getLocalizedMessage(),
				ex.getLocalizedMessage()
		), HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler(MethodArgumentNotValidException.class)
	public final ResponseEntity<ApiError> methodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
		List<String> errors = new ArrayList<>();

		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		return new ResponseEntity<>(new ApiError(
				HttpStatus.BAD_REQUEST,
				ex.getLocalizedMessage(),
				errors
		), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {

		return new ResponseEntity<>(new ApiError(
				HttpStatus.INTERNAL_SERVER_ERROR,
				ex.getLocalizedMessage(),
				ex.getLocalizedMessage()
		), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
