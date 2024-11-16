package com.dpbr.dpbrbe.domain.character.domain;

import com.dpbr.dpbrbe.domain.user.presentation.dto.response.CharacterInfoResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "characters")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Character {

	@Id
	String ocid;

	@NotNull
	@Column
	String name;

	@NotNull
	@Column
	String gender;

	@NotNull
	@Column
	String world;

	@NotNull
	@Column
	String job;

	@NotNull
	@Column(name = "character_level")
	Integer level;

	@Column
	Integer unionLevel;

	@NotNull
	@Column
	Long combatPower;

	@NotNull
	@Column(length = 500)
	String characterImage;

	@Builder
	private Character(String ocid, String name, String gender, String world, String job, Integer level,
		Integer unionLevel, Long combatPower, String characterImage) {
		this.ocid = ocid;
		this.name = name;
		this.gender = gender;
		this.world = world;
		this.job = job;
		this.level = level;
		this.unionLevel = unionLevel;
		this.combatPower = combatPower;
		this.characterImage = characterImage;
	}

	public static Character of(String ocid, String name, CharacterInfoResponse characterInfo) {
		return Character.builder()
			.ocid(ocid)
			.name(name)
			.gender(characterInfo.gender())
			.world(characterInfo.world())
			.job(characterInfo.job())
			.level(characterInfo.level())
			.unionLevel(characterInfo.unionLevel())
			.combatPower(characterInfo.combatPower())
			.characterImage(characterInfo.characterImage())
			.build();
	}
}
