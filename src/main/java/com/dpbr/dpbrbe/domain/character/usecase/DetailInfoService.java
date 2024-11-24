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
import com.dpbr.dpbrbe.domain.character.presentation.dto.response.CharacterDetailInfoResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DetailInfoService {

	private final RankService rankService;
	private final CharacterRepository characterRepository;

	public CharacterDetailInfoResponse execute(SearchRequest request) {
		// 캐릭터 정보 조회
		Character character = characterRepository.findByName(request.name())
			.orElseThrow(CharacterNotFoundException::new);

		// 동일 월드, 직업 캐릭터 수 조회
		Integer sameWorldCharacterCount = characterRepository.countByWorld(character.getWorld());
		Integer sameJobCharacterCount = characterRepository.countByJob(character.getJob());

		// 레벨 상위 몇 %인지 조회
		List<CharacterRankingResponse> levelRanking = rankService.level();
		BigDecimal levelPercentage = calculatePercentage(levelRanking, character.getName());

		// 유니온 레벨 상위 몇 %인지 조회
		List<CharacterRankingResponse> unionRanking = rankService.unionLevel();
		BigDecimal unionPercentage = calculatePercentage(unionRanking, character.getName());

		// 전투력 상위 몇 %인지 조회
		List<CharacterRankingResponse> combatPowerRanking = rankService.combatPower();
		BigDecimal combatPowerPercentage = calculatePercentage(combatPowerRanking, character.getName());

		// 응답 생성
		return CharacterDetailInfoResponse.of(CharacterInfoResponse.from(character), sameWorldCharacterCount,
			sameJobCharacterCount,
			levelPercentage, unionPercentage, combatPowerPercentage);
	}

	// 상위 몇 %인지 계산
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
