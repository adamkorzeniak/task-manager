package com.adamkorzeniak.taskmanager.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.dao.DataIntegrityViolationException;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { DuplicateUserException.class })
	protected ResponseEntity<Object> duplicateUser(RuntimeException ex, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse("Username already taken");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
	
	@ExceptionHandler(value = { ParentTaskNotFoundException.class })
	protected ResponseEntity<Object> parentTaskNotFound(RuntimeException ex, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse("Parent task not found");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler(value = { ParentOfItselfException.class })
	protected ResponseEntity<Object> parentOfItself(RuntimeException ex, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse("Task cannot be parent of itself");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler(value = { IllegalArgumentException.class })
	protected ResponseEntity<Object> badRequest(RuntimeException ex, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse("Bad Request");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = { UnauthorizedUserException.class })
	protected ResponseEntity<Object> accessDenied(RuntimeException ex, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse("You do not have rights to perform this operation");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
	}

	@ExceptionHandler(value = { DataIntegrityViolationException.class })
	protected ResponseEntity<Object> dataInvalid(RuntimeException ex, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse("Request data violates database integrity");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
	}

	@ExceptionHandler(value = { ConstraintViolationException.class })
	protected ResponseEntity<Object> constraintsViolated(RuntimeException ex, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse("Database constraint have been violated");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
	}
}