package com.dpbr.dpbrbe.domain.character.presentation.dto.response;

import lombok.Builder;

@Builder
public record CharacterRankingResponse(
	Long ranking,
	CharacterInfoResponse info) {

	public static CharacterRankingResponse of(Long ranking, CharacterInfoResponse info) {
		return CharacterRankingResponse.builder()
			.ranking(ranking)
			.info(info)
			.build();
	}
}
