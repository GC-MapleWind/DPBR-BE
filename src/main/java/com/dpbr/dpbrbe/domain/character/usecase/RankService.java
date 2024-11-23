package com.dpbr.dpbrbe.domain.character.usecase;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.dpbr.dpbrbe.domain.character.domain.Character;
import com.dpbr.dpbrbe.domain.character.domain.repository.CharacterRepository;
import com.dpbr.dpbrbe.domain.character.presentation.dto.response.CharacterInfoResponse;
import com.dpbr.dpbrbe.domain.character.presentation.dto.response.CharacterRankingResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RankService {

	private final CharacterRepository characterRepository;

	public List<CharacterRankingResponse> level() {
		AtomicLong rank = new AtomicLong(1);

		return characterRepository.findAllByOrderByLevelDesc().stream()
			.map(character -> CharacterRankingResponse.of(
				rank.getAndIncrement(),
				CharacterInfoResponse.from(character)))
			.toList();
	}

	public List<CharacterRankingResponse> combatPower() {
		AtomicLong rank = new AtomicLong(1);

		return characterRepository.findAllByOrderByCombatPowerDesc().stream()
			.map(character -> CharacterRankingResponse.of(
				rank.getAndIncrement(),
				CharacterInfoResponse.from(character)))
			.toList();
	}

	public List<CharacterRankingResponse> unionLevel() {
		AtomicLong rank = new AtomicLong(1);

		return characterRepository.findAllByOrderByUnionLevelDesc().stream()
			.map(character -> CharacterRankingResponse.of(
				rank.getAndIncrement(),
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
