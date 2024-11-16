package com.dpbr.dpbrbe.domain.user.presentation.dto.response;

import com.dpbr.dpbrbe.global.openAPI.dto.response.CharacterBasicInfoResponse;
import com.dpbr.dpbrbe.global.openAPI.dto.response.CharacterStatInfoResponse;
import com.dpbr.dpbrbe.global.openAPI.dto.response.CharacterUnionInfoResponse;

public record CharacterInfoResponse(
	String gender,
	String world,
	String job,
	Integer level,
	Integer unionLevel,
	Long combatPower,
	String characterImage
) {

	private static final long INVALID_LONG_VALUE = -1L;

	public static CharacterInfoResponse of(CharacterBasicInfoResponse basicInfo, CharacterUnionInfoResponse unionInfo, CharacterStatInfoResponse statInfo) {
		Long combatPower = statInfo.finalStats().stream()
			.filter(stat -> "전투력".equals(stat.statName()))
			.map(stat -> Long.valueOf(stat.statValue()))
			.findFirst()
			.orElse(INVALID_LONG_VALUE);

		return new CharacterInfoResponse(
			basicInfo.characterGender(),
			basicInfo.worldName(),
			basicInfo.characterClass(),
			basicInfo.characterLevel(),
			unionInfo.unionLevel(),
			combatPower,
			basicInfo.characterImage());
	}
}
