package com.logistics.payment.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApiResponse<T> {

	private boolean success;

	private String errorCode;

	private String message;

	private T data;

	private LocalDateTime timestamp;

	public static <T> ApiResponse<T> success(T data, String message) {
		return ApiResponse.<T>builder().success(true).message(message).data(data).timestamp(LocalDateTime.now())
				.build();
	}

	public static <T> ApiResponse<T> error(String errorCode, String message) {
		return ApiResponse.<T>builder().success(false).errorCode(errorCode).message(message)
				.timestamp(LocalDateTime.now()).build();
	}
}