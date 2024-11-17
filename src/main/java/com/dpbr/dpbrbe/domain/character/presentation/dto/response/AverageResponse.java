package com.dpbr.dpbrbe.domain.character.presentation.dto.response;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record AverageResponse(BigDecimal average) {

	public static AverageResponse form(BigDecimal average) {
		return AverageResponse.builder()
			.average(average)
			.build();
	}
}
