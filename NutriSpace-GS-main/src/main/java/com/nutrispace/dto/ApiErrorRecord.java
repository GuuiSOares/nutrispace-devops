package com.nutrispace.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiErrorRecord(
		LocalDateTime timestamp,
		int status,
		String message,
		Map<String, String> errors) {

	public static ApiErrorRecord of(int status, String message) {
		return new ApiErrorRecord(LocalDateTime.now(), status, message, null);
	}

	public static ApiErrorRecord validation(int status, String message, Map<String, String> errors) {
		return new ApiErrorRecord(LocalDateTime.now(), status, message, errors);
	}
}
