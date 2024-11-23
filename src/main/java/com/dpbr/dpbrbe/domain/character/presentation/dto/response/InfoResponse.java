package com.dpbr.dpbrbe.domain.character.presentation.dto.response;

import com.dpbr.dpbrbe.domain.character.domain.Character;
import com.dpbr.dpbrbe.global.openAPI.dto.response.CharacterBasicInfoResponse;
import com.dpbr.dpbrbe.global.openAPI.dto.response.CharacterStatInfoResponse;
import com.dpbr.dpbrbe.global.openAPI.dto.response.CharacterUnionInfoResponse;

import lombok.Builder;

@Builder
public record InfoResponse(
	String name,
	String gender,
	String world,
	String job,
	Integer level,
	Integer unionLevel,
	Long combatPower,
	String characterImage
) {

	private static final long INVALID_LONG_VALUE = -1L;

	public static InfoResponse of(CharacterBasicInfoResponse basicInfo, CharacterUnionInfoResponse unionInfo,
		CharacterStatInfoResponse statInfo) {
		Long combatPower = statInfo.finalStats().stream()
			.filter(stat -> "전투력".equals(stat.statName()))
			.map(stat -> Long.valueOf(stat.statValue()))
			.findFirst()
			.orElse(INVALID_LONG_VALUE);

		return InfoResponse.builder()
			.name(basicInfo.characterName())
			.gender(basicInfo.characterGender())
			.world(basicInfo.worldName())
			.job(basicInfo.characterClass())
			.level(basicInfo.characterLevel())
			.unionLevel(unionInfo.unionLevel())
			.combatPower(combatPower)
			.characterImage(basicInfo.characterImage())
			.build();
	}

	public static InfoResponse from(Character character) {
		return InfoResponse.builder()
			.name(character.getName())
			.gender(character.getGender())
			.world(character.getWorld())
			.job(character.getJob())
			.level(character.getLevel())
			.unionLevel(character.getUnionLevel())
			.combatPower(character.getCombatPower())
			.characterImage(character.getCharacterImage())
			.build();
	}
}
