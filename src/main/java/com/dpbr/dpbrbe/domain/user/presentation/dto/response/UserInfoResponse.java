package com.dpbr.dpbrbe.domain.user.presentation.dto.response;

import com.dpbr.dpbrbe.domain.character.domain.Character;
import com.dpbr.dpbrbe.domain.character.presentation.dto.response.CharacterInfoResponse;
import com.dpbr.dpbrbe.domain.user.domain.User;

import lombok.Builder;

@Builder
public record UserInfoResponse(
	String name,
	String major,
	String email,
	CharacterInfoResponse info
) {
	public static UserInfoResponse of(User user, Character character) {
		return UserInfoResponse.builder()
			.name(user.getName())
			.major(user.getMajor())
			.email(user.getEmail())
			.info(CharacterInfoResponse.from(character))
			.build();
	}
}
