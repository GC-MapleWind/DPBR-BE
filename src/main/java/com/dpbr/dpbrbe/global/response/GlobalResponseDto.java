package com.dpbr.dpbrbe.global.response;

import java.time.LocalDateTime;

import com.dpbr.dpbrbe.global.error.ErrorCode;
import com.dpbr.dpbrbe.global.success.SuccessCode;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GlobalResponseDto<T> {
	private boolean isSuccess;
	private String code;
	private LocalDateTime timestamp;
	private String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T result;

	@Builder
	public GlobalResponseDto(boolean isSuccess, String code, LocalDateTime timestamp, String message, T result) {
		this.isSuccess = isSuccess;
		this.code = code;
		this.timestamp = timestamp;
		this.message = message;
		this.result = result;
	}
	
	public static <T> GlobalResponseDto<T> success(T result, SuccessCode code) {
		return GlobalResponseDto.<T>builder()
			.isSuccess(true)
			.code(code.getCode())
			.timestamp(LocalDateTime.now())
			.message(code.getMessage())
			.result(result)
			.build();
	}
	
	public static <T> GlobalResponseDto<T> success(SuccessCode code) {
		return GlobalResponseDto.<T>builder()
			.isSuccess(true)
			.code(code.getCode())
			.timestamp(LocalDateTime.now())
			.message(code.getMessage())
			.result(null)
			.build();
	}
	
	public static <T> GlobalResponseDto<T> fail(ErrorCode code) {
		return GlobalResponseDto.<T>builder()
			.isSuccess(false)
			.code(code.getCode())
			.timestamp(LocalDateTime.now())
			.message(code.getMessage())
			.result(null)
			.build();
	}
}
