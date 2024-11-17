package com.dpbr.dpbrbe.global.success;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {

	SUCCESS(200, "SUCCESS", "성공"),
	UPDATE_CHARACTER_SUCCESS(200, "UPDATE_CHARACTER_SUCCESS", "캐릭터 정보를 성공적으로 업데이트했습니다."),
	CREATED(201, "CREATED", "생성 완료"),

	SAVE_CHARACTER_SUCCESS(201, "SAVE_CHARACTER_SUCCESS", "캐릭터를 성공적으로 저장했습니다.");

	private final int httpStatus;
	private final String code;
	private final String message;
}
