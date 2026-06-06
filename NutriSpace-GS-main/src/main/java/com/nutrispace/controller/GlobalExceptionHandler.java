package com.nutrispace.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.nutrispace.exception.BusinessException;
import com.nutrispace.exception.ResourceNotFoundException;
import com.nutrispace.dto.ApiErrorRecord;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiErrorRecord> handleResourceNotFound(ResourceNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ApiErrorRecord.of(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiErrorRecord> handleBusiness(BusinessException ex) {
		return ResponseEntity.badRequest()
				.body(ApiErrorRecord.of(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ApiErrorRecord> handleDataIntegrity(DataIntegrityViolationException ex) {
		String message = "Violação de integridade dos dados";
		String cause = ex.getMostSpecificCause().getMessage();
		if (cause != null && cause.contains("ORA-00001")) {
			message = "Registro duplicado: verifique se a sequence Oracle está sincronizada com os IDs existentes";
		}
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(ApiErrorRecord.of(HttpStatus.CONFLICT.value(), message));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiErrorRecord> handleUnreadableJson(HttpMessageNotReadableException ex) {
		return ResponseEntity.badRequest()
				.body(ApiErrorRecord.of(HttpStatus.BAD_REQUEST.value(), "JSON inválido no corpo da requisição"));
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ApiErrorRecord> handleNoResource(NoResourceFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ApiErrorRecord.of(HttpStatus.NOT_FOUND.value(), "Recurso não encontrado: " + ex.getResourcePath()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorRecord> handleValidation(MethodArgumentNotValidException ex) {
		Map<String, String> fieldErrors = new HashMap<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			fieldErrors.put(error.getField(), error.getDefaultMessage());
		}
		return ResponseEntity.badRequest().body(ApiErrorRecord.validation(
				HttpStatus.BAD_REQUEST.value(),
				"Erro de validação nos campos informados",
				fieldErrors));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorRecord> handleGeneric(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ApiErrorRecord.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno no servidor"));
	}
}
