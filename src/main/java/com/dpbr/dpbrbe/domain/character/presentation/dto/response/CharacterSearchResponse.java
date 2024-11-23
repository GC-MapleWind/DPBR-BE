package com.dpbr.dpbrbe.domain.character.presentation.dto.response;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record CharacterSearchResponse(
	CharacterInfoResponse info,
	Integer sameWorldCharacterCount,
	Integer sameJobCharacterCount,
	BigDecimal levelPercentage,
	BigDecimal unionPercentage,
	BigDecimal combatPowerPercentage
) {
	public static CharacterSearchResponse of(CharacterInfoResponse info, Integer sameWorldCharacterCount, Integer sameJobCharacterCount,
		BigDecimal levelPercentage, BigDecimal unionPercentage, BigDecimal combatPowerPercentage) {
		return CharacterSearchResponse.builder()
			.info(info)
			.sameWorldCharacterCount(sameWorldCharacterCount)
			.sameJobCharacterCount(sameJobCharacterCount)
			.levelPercentage(levelPercentage)
			.unionPercentage(unionPercentage)
			.combatPowerPercentage(combatPowerPercentage)
			.build();
	}
}
