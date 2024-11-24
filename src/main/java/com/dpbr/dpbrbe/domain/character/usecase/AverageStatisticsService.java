package com.dpbr.dpbrbe.domain.character.usecase;

import org.springframework.stereotype.Service;

import com.dpbr.dpbrbe.domain.character.domain.Character;
import com.dpbr.dpbrbe.domain.character.domain.repository.CharacterRepository;
import com.dpbr.dpbrbe.domain.character.presentation.dto.response.CharacterAverageResponse;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

@Service
@AllArgsConstructor
public class AverageStatisticsService {

	private final CharacterRepository characterRepository;

	// 캐릭터의 전체 수 조회
	public Integer count() {
		return characterRepository.findAll().size();
	}

	// 레벨 평균 조회
	public CharacterAverageResponse level() {
		return calculateAverage(characterRepository.findAll(), Character::getLevel);
	}

	// 전투력 평균 조회
	public CharacterAverageResponse combatPower() {
		return calculateAverage(characterRepository.findAll(), Character::getCombatPower);
	}

	// 유니온 레벨 평균 조회
	public CharacterAverageResponse unionLevel() {
		return calculateAverage(characterRepository.findAll(), Character::getUnionLevel);
	}

	// 평균 산출 (int)
	private CharacterAverageResponse calculateAverage(List<Character> characters, ToIntFunction<Character> mapper) {
		if (characters.isEmpty()) {
			return CharacterAverageResponse.form(BigDecimal.ZERO);
		}
		BigDecimal total = characters.stream()
			.map(character -> BigDecimal.valueOf(mapper.applyAsInt(character)))
			.reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal average = total.divide(BigDecimal.valueOf(characters.size()), 4, RoundingMode.HALF_UP);
		return CharacterAverageResponse.form(average);
	}

	// 평균 산출 (long)
	private CharacterAverageResponse calculateAverage(List<Character> characters, ToLongFunction<Character> mapper) {
		if (characters.isEmpty()) {
			return CharacterAverageResponse.form(BigDecimal.ZERO);
		}
		BigDecimal total = characters.stream()
			.map(character -> BigDecimal.valueOf(mapper.applyAsLong(character)))
			.reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal average = total.divide(BigDecimal.valueOf(characters.size()), 4, RoundingMode.HALF_UP);
		return CharacterAverageResponse.form(average);
	}
}
