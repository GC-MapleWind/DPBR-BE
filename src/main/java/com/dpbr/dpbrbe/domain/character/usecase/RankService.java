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

	// 레벨 순위 조회
	public List<CharacterRankingResponse> level() {
		AtomicLong rank = new AtomicLong(1); // 순위

		// 레벨 순으로 정렬하여 순위와 캐릭터 정보를 반환
		return characterRepository.findAllByOrderByLevelDesc().stream()
			.map(character -> CharacterRankingResponse.of(
				rank.getAndIncrement(),
				CharacterInfoResponse.from(character)))
			.toList();
	}

	// 전투력 순위 조회
	public List<CharacterRankingResponse> combatPower() {
		AtomicLong rank = new AtomicLong(1);

		// 전투력 순으로 정렬하여 순위와 캐릭터 정보를 반환
		return characterRepository.findAllByOrderByCombatPowerDesc().stream()
			.map(character -> CharacterRankingResponse.of(
				rank.getAndIncrement(),
				CharacterInfoResponse.from(character)))
			.toList();
	}

	// 유니온 레벨 순위 조회
	public List<CharacterRankingResponse> unionLevel() {
		AtomicLong rank = new AtomicLong(1);

		// 유니온 레벨 순으로 정렬하여 순위와 캐릭터 정보를 반환
		return characterRepository.findAllByOrderByUnionLevelDesc().stream()
			.map(character -> CharacterRankingResponse.of(
				rank.getAndIncrement(),
				CharacterInfoResponse.from(character)))
			.toList();
	}

	// 최상위 레벨 캐릭터 정보 조회
	public CharacterInfoResponse topLevel() {
		return CharacterInfoResponse.from(characterRepository.findAllByOrderByLevelDesc().get(0));
	}

	// 최상위 전투력 캐릭터 정보 조회
	public CharacterInfoResponse topCombatPower() {
		return CharacterInfoResponse.from(characterRepository.findAllByOrderByCombatPowerDesc().get(0));
	}

	// 최상위 유니온 레벨 캐릭터 정보 조회
	public CharacterInfoResponse topUnionLevel() {
		return CharacterInfoResponse.from(characterRepository.findAllByOrderByUnionLevelDesc().get(0));
	}
}
