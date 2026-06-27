package com.logistics.utils;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
	private HttpStatus status;
	private String message;
	private T data;
	private LocalDateTime time;
	
	public ApiResponse(HttpStatus status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
		this.time = LocalDateTime.now();
	}
}
