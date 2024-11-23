package com.dpbr.dpbrbe.domain.character.presentation.dto.response;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record CharacterAverageResponse(BigDecimal average) {

	public static CharacterAverageResponse form(BigDecimal average) {
		return CharacterAverageResponse.builder()
			.average(average)
			.build();
	}
}
