package com.dpbr.dpbrbe.domain.character.usecase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dpbr.dpbrbe.domain.character.domain.Character;
import com.dpbr.dpbrbe.domain.character.domain.repository.CharacterRepository;
import com.dpbr.dpbrbe.domain.character.exception.CharacterNotFoundException;
import com.dpbr.dpbrbe.domain.character.presentation.dto.request.SearchRequest;
import com.dpbr.dpbrbe.domain.character.presentation.dto.response.InfoResponse;
import com.dpbr.dpbrbe.domain.character.presentation.dto.response.RankingResponse;
import com.dpbr.dpbrbe.domain.character.presentation.dto.response.SearchResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SearchService {

	private final RankService rankService;
	private final CharacterRepository characterRepository;

	public SearchResponse execute(SearchRequest request) {
		Character character = characterRepository.findByName(request.name())
			.orElseThrow(CharacterNotFoundException::new);

		Integer sameWorldCharacterCount = characterRepository.countByWorld(character.getWorld());
		Integer sameJobCharacterCount = characterRepository.countByJob(character.getJob());

		List<RankingResponse> levelRanking = rankService.level();
		BigDecimal levelPercentage = calculatePercentage(levelRanking, character.getName());

		List<RankingResponse> unionRanking = rankService.unionLevel();
		BigDecimal unionPercentage = calculatePercentage(unionRanking, character.getName());

		List<RankingResponse> combatPowerRanking = rankService.combatPower();
		BigDecimal combatPowerPercentage = calculatePercentage(combatPowerRanking, character.getName());

		return SearchResponse.of(InfoResponse.from(character), sameWorldCharacterCount, sameJobCharacterCount,
			levelPercentage, unionPercentage, combatPowerPercentage);
	}

	private BigDecimal calculatePercentage(List<RankingResponse> rankings, String characterName) {
		return rankings.stream()
			.filter(ranking -> ranking.info().name().equals(characterName))
			.map(RankingResponse::ranking)
			.map(rank -> BigDecimal.valueOf(rank)
				.divide(BigDecimal.valueOf(rankings.size()), 2, RoundingMode.HALF_UP)
				.multiply(BigDecimal.valueOf(100)))
			.findFirst()
			.orElseThrow(CharacterNotFoundException::new);
	}
}
