package com.dpbr.dpbrbe.domain.character.presentation.dto.response;

import com.dpbr.dpbrbe.domain.character.domain.Character;

import lombok.Builder;

@Builder
public record InfoResponse(
	String name,
	String gender,
	String world,
	String job,
	Integer level,
	Long combatPower,
	String characterImage
) {

	public static InfoResponse from(Character character) {
		return InfoResponse.builder()
			.name(character.getName())
			.gender(character.getGender())
			.world(character.getWorld())
			.job(character.getJob())
			.level(character.getLevel())
			.combatPower(character.getCombatPower())
			.characterImage(character.getCharacterImage())
			.build();
	}

}
