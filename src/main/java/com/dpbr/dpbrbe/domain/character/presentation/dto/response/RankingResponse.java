package com.dpbr.dpbrbe.domain.character.presentation.dto.response;

import lombok.Builder;

@Builder
public record RankingResponse(
	Long ranking,
	CharacterInfoResponse info) {

	public static RankingResponse of(Long ranking, CharacterInfoResponse info) {
		return RankingResponse.builder()
			.ranking(ranking)
			.info(info)
			.build();
	}
}
