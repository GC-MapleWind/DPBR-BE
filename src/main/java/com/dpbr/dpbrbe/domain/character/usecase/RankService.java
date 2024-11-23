package com.dpbr.dpbrbe.domain.character.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dpbr.dpbrbe.domain.character.domain.repository.CharacterRepository;
import com.dpbr.dpbrbe.domain.character.presentation.dto.response.CharacterInfoResponse;
import com.dpbr.dpbrbe.domain.character.presentation.dto.response.CharacterRankingResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RankService {

	private final CharacterRepository characterRepository;

	public List<CharacterRankingResponse> level() {
		return characterRepository.findAllByOrderByLevelDesc().stream()
			.map(character -> CharacterRankingResponse.of(
				(long)(characterRepository.findAllByOrderByLevelDesc().indexOf(character) + 1),
				CharacterInfoResponse.from(character)))
			.toList();
	}

	public List<CharacterRankingResponse> combatPower() {
		return characterRepository.findAllByOrderByCombatPowerDesc().stream()
			.map(character -> CharacterRankingResponse.of(
				(long)(characterRepository.findAllByOrderByLevelDesc().indexOf(character) + 1),
				CharacterInfoResponse.from(character)))
			.toList();
	}

	public List<CharacterRankingResponse> unionLevel() {
		return characterRepository.findAllByOrderByUnionLevelDesc().stream()
			.map(character -> CharacterRankingResponse.of(
				(long)(characterRepository.findAllByOrderByLevelDesc().indexOf(character) + 1),
				CharacterInfoResponse.from(character)))
			.toList();
	}

	public CharacterInfoResponse topLevel() {
		return CharacterInfoResponse.from(characterRepository.findAllByOrderByLevelDesc().get(0));
	}

	public CharacterInfoResponse topCombatPower() {
		return CharacterInfoResponse.from(characterRepository.findAllByOrderByCombatPowerDesc().get(0));
	}

	public CharacterInfoResponse topUnionLevel() {
		return CharacterInfoResponse.from(characterRepository.findAllByOrderByUnionLevelDesc().get(0));
	}
}
