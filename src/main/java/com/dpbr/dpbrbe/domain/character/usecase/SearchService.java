package com.dpbr.dpbrbe.domain.character.usecase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dpbr.dpbrbe.domain.character.domain.Character;
import com.dpbr.dpbrbe.domain.character.domain.repository.CharacterRepository;
import com.dpbr.dpbrbe.domain.character.exception.CharacterNotFoundException;
import com.dpbr.dpbrbe.domain.character.presentation.dto.request.SearchRequest;
import com.dpbr.dpbrbe.domain.character.presentation.dto.response.CharacterInfoResponse;
import com.dpbr.dpbrbe.domain.character.presentation.dto.response.CharacterRankingResponse;
import com.dpbr.dpbrbe.domain.character.presentation.dto.response.CharacterSearchResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SearchService {

	private final RankService rankService;
	private final CharacterRepository characterRepository;

	public CharacterSearchResponse execute(SearchRequest request) {
		Character character = characterRepository.findByName(request.name())
			.orElseThrow(CharacterNotFoundException::new);

		Integer sameWorldCharacterCount = characterRepository.countByWorld(character.getWorld());
		Integer sameJobCharacterCount = characterRepository.countByJob(character.getJob());

		List<CharacterRankingResponse> levelRanking = rankService.level();
		BigDecimal levelPercentage = calculatePercentage(levelRanking, character.getName());

		List<CharacterRankingResponse> unionRanking = rankService.unionLevel();
		BigDecimal unionPercentage = calculatePercentage(unionRanking, character.getName());

		List<CharacterRankingResponse> combatPowerRanking = rankService.combatPower();
		BigDecimal combatPowerPercentage = calculatePercentage(combatPowerRanking, character.getName());

		return CharacterSearchResponse.of(CharacterInfoResponse.from(character), sameWorldCharacterCount,
			sameJobCharacterCount,
			levelPercentage, unionPercentage, combatPowerPercentage);
	}

	private BigDecimal calculatePercentage(List<CharacterRankingResponse> rankings, String characterName) {
		return rankings.stream()
			.filter(ranking -> ranking.info().name().equals(characterName))
			.map(CharacterRankingResponse::ranking)
			.map(rank -> BigDecimal.valueOf(rank)
				.divide(BigDecimal.valueOf(rankings.size()), 4, RoundingMode.HALF_UP)
				.multiply(BigDecimal.valueOf(100)))
			.findFirst()
			.orElseThrow(CharacterNotFoundException::new);
	}
}
