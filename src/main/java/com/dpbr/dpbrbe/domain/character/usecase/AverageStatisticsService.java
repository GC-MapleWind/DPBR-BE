package com.dpbr.dpbrbe.domain.character.usecase;

import org.springframework.stereotype.Service;

import com.dpbr.dpbrbe.domain.character.domain.Character;
import com.dpbr.dpbrbe.domain.character.domain.repository.CharacterRepository;
import com.dpbr.dpbrbe.domain.character.presentation.dto.response.AverageResponse;

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

	public AverageResponse level() {
		return calculateAverage(characterRepository.findAll(), Character::getLevel);
	}

	public AverageResponse combatPower() {
		return calculateAverage(characterRepository.findAll(), Character::getCombatPower);
	}

	public AverageResponse unionLevel() {
		return calculateAverage(characterRepository.findAll(), Character::getUnionLevel);
	}

	private AverageResponse calculateAverage(List<Character> characters, ToIntFunction<Character> mapper) {
		if (characters.isEmpty()) {
			return AverageResponse.form(BigDecimal.ZERO);
		}
		BigDecimal total = characters.stream()
			.map(character -> BigDecimal.valueOf(mapper.applyAsInt(character)))
			.reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal average = total.divide(BigDecimal.valueOf(characters.size()), 4, RoundingMode.HALF_UP);
		return AverageResponse.form(average);
	}

	private AverageResponse calculateAverage(List<Character> characters, ToLongFunction<Character> mapper) {
		if (characters.isEmpty()) {
			return AverageResponse.form(BigDecimal.ZERO);
		}
		BigDecimal total = characters.stream()
			.map(character -> BigDecimal.valueOf(mapper.applyAsLong(character)))
			.reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal average = total.divide(BigDecimal.valueOf(characters.size()), 4, RoundingMode.HALF_UP);
		return AverageResponse.form(average);
	}
}
