package com.dpbr.dpbrbe.domain.character.presentation.dto.response;

import lombok.Builder;

@Builder
public record RankingResponse(
	Long ranking,
	InfoResponse info) {

	public static RankingResponse of(Long ranking, InfoResponse info) {
		return RankingResponse.builder()
			.ranking(ranking)
			.info(info)
			.build();
	}
}
