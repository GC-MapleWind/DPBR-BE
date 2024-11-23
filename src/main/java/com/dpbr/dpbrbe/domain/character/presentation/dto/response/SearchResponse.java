package com.dpbr.dpbrbe.domain.character.presentation.dto.response;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record SearchResponse(
	InfoResponse info,
	Integer sameWorldCharacterCount,
	Integer sameJobCharacterCount,
	BigDecimal levelPercentage,
	BigDecimal unionPercentage,
	BigDecimal combatPowerPercentage
) {
	public static SearchResponse of(InfoResponse info, Integer sameWorldCharacterCount, Integer sameJobCharacterCount,
		BigDecimal levelPercentage, BigDecimal unionPercentage, BigDecimal combatPowerPercentage) {
		return SearchResponse.builder()
			.info(info)
			.sameWorldCharacterCount(sameWorldCharacterCount)
			.sameJobCharacterCount(sameJobCharacterCount)
			.levelPercentage(levelPercentage)
			.unionPercentage(unionPercentage)
			.combatPowerPercentage(combatPowerPercentage)
			.build();
	}
}
