package com.parking.lot.config;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.persistence.EntityNotFoundException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.PropertyAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.parking.lot.dto.DependencyException;
import com.parking.lot.dto.DuplicateRecordException;
import com.parking.lot.dto.ExceptionObject;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice
public class ExceptionInterceptor {

	/************************
	 * Framework Specific Exceptions
	 ************************/

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionObject> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error("Got MethodArgumentNotValidException: ", e);
		FieldError fieldError = e.getBindingResult().getFieldError();
		String errorMessage = fieldError.getDefaultMessage();
		if (errorMessage == null || errorMessage.equals("")) {
			errorMessage = "Invalid Value: " + fieldError.getRejectedValue() + " for Field: " + fieldError.getField();
		}
		ExceptionObject exceptionObject = new ExceptionObject(errorMessage, e.getClass().getSimpleName());
		return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(PropertyAccessException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionObject> handlePropertyAccessException(PropertyAccessException e) {
		log.error("Got PropertyAccessException:", e);
		String errorMessage = "Incorrect Value For Parameter: " + e.getPropertyName() + " in Request. Dirty Value: "
				+ e.getValue();
		ExceptionObject exceptionObject = new ExceptionObject(errorMessage, e.getClass().getSimpleName());
		return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionObject> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException e) {
		log.error("Got MethodArgumentTypeMismatchException: ", e);
		String errorMessage = "Incorrect Type For Parameter: " + e.getName() + " in Request. Expected: "
				+ e.getRequiredType() + ", Found: " + e.getValue();
		ExceptionObject exceptionObject = new ExceptionObject(errorMessage, e.getClass().getSimpleName());
		return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionObject> handleMissingServletRequestParameterException(
			MissingServletRequestParameterException e) {
		log.error("Got MissingServletRequestParameterException: ", e);
		String errorMessage = "Missing Parameter: " + e.getParameterName() + " in Request";
		ExceptionObject exceptionObject = new ExceptionObject(errorMessage, e.getClass().getSimpleName());
		return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BindException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionObject> handleBindException(BindException e) {
		log.error("Got MissingServletRequestParameterException: ", e);
		BindingResult bindingResult = e.getBindingResult();
		StringBuilder fieldErrors = new StringBuilder();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			fieldErrors = fieldErrors.append("{FieldName: ").append(fieldError.getField()).append(", RejectedValue: ")
					.append(fieldError.getRejectedValue()).append("} ");
		}
		String errorMessage = "Found: " + bindingResult.getErrorCount() + " Errors in Request. Fields With Error: ["
				+ fieldErrors.toString() + "]";
		ExceptionObject exceptionObject = new ExceptionObject(errorMessage, e.getClass().getSimpleName());
		return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionObject> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		log.error("Got HttpMessageNotReadableException: ", e);
		ExceptionObject exceptionObject = new ExceptionObject(e.getMessage(), e.getClass().getSimpleName());
		return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidFormatException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionObject> handleInvalidFormatException(InvalidFormatException e) {
		log.error("Got InvalidFormatException: ", e);
		ExceptionObject exceptionObject = new ExceptionObject(e.getMessage(), e.getClass().getSimpleName());
		return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionObject> handleSQLIntegrityConstraintViolationException(
			SQLIntegrityConstraintViolationException e) {
		log.error("Got SQLIntegrityConstraintViolationException:", e);
		ExceptionObject exceptionObject = new ExceptionObject(e.getMessage(), e.getClass().getSimpleName());
		return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionObject> handleConstraintViolationException(ConstraintViolationException e) {
		log.error("Got ConstraintViolationException: ", e);
		ExceptionObject exceptionObject = new ExceptionObject(e.getMessage(), e.getClass().getSimpleName());
		return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionObject> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
		log.error("Got DataIntegrityViolationException:", e);
		ExceptionObject exceptionObject = new ExceptionObject(e.getMessage(), e.getClass().getSimpleName());
		return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
	}

	/************************
	 * Application Specific Exceptions
	 ************************/

	@ExceptionHandler(DuplicateRecordException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionObject> handleDuplicateRecordException(DuplicateRecordException e) {
		log.error("Got DuplicateRecordException: ", e);
		ExceptionObject exceptionObject = new ExceptionObject(e.getMessage(), e.getClass().getSimpleName());
		return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResponseEntity<ExceptionObject> handleEntityNotFoundException(EntityNotFoundException e) {
		log.error("Got EntityNotFoundException: ", e);
		ExceptionObject exceptionObject = new ExceptionObject(e.getMessage(), e.getClass().getSimpleName());
		return new ResponseEntity<>(exceptionObject, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DependencyException.class)
	@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
	public ResponseEntity<ExceptionObject> handleDependencyException(DependencyException e) {
		log.error("Got DependencyException: ", e);
		ExceptionObject exceptionObject = new ExceptionObject(e.getMessage(), e.getClass().getSimpleName());
		return new ResponseEntity<>(exceptionObject, HttpStatus.PRECONDITION_FAILED);
	}

	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ExceptionObject> handleNullPointerException(NullPointerException e) {
		log.error("Got un-handled exception: ", e);
		ExceptionObject exceptionObject = new ExceptionObject(e.getMessage(), e.getClass().getSimpleName());
		return new ResponseEntity<>(exceptionObject, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ExceptionObject> handleException(Exception e) {
		log.error("Got un-handled exception: ", e);
		ExceptionObject exceptionObject = new ExceptionObject(e.getMessage(), e.getClass().getSimpleName());
		return new ResponseEntity<>(exceptionObject, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}