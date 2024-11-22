package com.dpbr.dpbrbe.domain.character.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dpbr.dpbrbe.domain.character.domain.repository.CharacterRepository;
import com.dpbr.dpbrbe.domain.character.presentation.dto.response.CharacterInfoResponse;
import com.dpbr.dpbrbe.domain.character.presentation.dto.response.RankingResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RankService {

	private final CharacterRepository characterRepository;

	public List<RankingResponse> level() {
		return characterRepository.findAllByOrderByLevelDesc().stream()
			.map(character -> RankingResponse.of(
				(long)(characterRepository.findAllByOrderByLevelDesc().indexOf(character) + 1),
				CharacterInfoResponse.from(character)))
			.toList();
	}

	public List<RankingResponse> combatPower() {
		return characterRepository.findAllByOrderByCombatPowerDesc().stream()
			.map(character -> RankingResponse.of(
				(long)(characterRepository.findAllByOrderByLevelDesc().indexOf(character) + 1),
				CharacterInfoResponse.from(character)))
			.toList();
	}

	public List<RankingResponse> unionLevel() {
		return characterRepository.findAllByOrderByUnionLevelDesc().stream()
			.map(character -> RankingResponse.of(
				(long)(characterRepository.findAllByOrderByLevelDesc().indexOf(character) + 1),
				CharacterInfoResponse.from(character)))
			.toList();
	}
}
