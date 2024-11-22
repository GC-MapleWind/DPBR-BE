package com.dpbr.dpbrbe.domain.character.presentation;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dpbr.dpbrbe.domain.character.presentation.dto.request.SearchRequest;
import com.dpbr.dpbrbe.domain.character.presentation.dto.request.UpdateRequest;
import com.dpbr.dpbrbe.domain.character.presentation.dto.response.AverageResponse;
import com.dpbr.dpbrbe.domain.character.presentation.dto.response.CharacterInfoResponse;
import com.dpbr.dpbrbe.domain.character.presentation.dto.response.RankingResponse;
import com.dpbr.dpbrbe.domain.character.usecase.AverageStatisticsService;
import com.dpbr.dpbrbe.domain.character.usecase.RankService;
import com.dpbr.dpbrbe.domain.character.usecase.SearchService;
import com.dpbr.dpbrbe.domain.character.usecase.UpdateInfoService;
import com.dpbr.dpbrbe.global.error.ErrorResponse;
import com.dpbr.dpbrbe.global.response.GlobalResponseDto;
import com.dpbr.dpbrbe.global.success.SuccessCode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("v1/character")
@RequiredArgsConstructor
public class CharacterController {

	private final RankService rankService;
	private final SearchService searchService;
	private final UpdateInfoService updateInfoService;
	private final AverageStatisticsService averageStatisticsService;

	@Operation(summary = "캐릭터 정보 갱신", description = "사용자 캐릭터의 정보를 갱신합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PatchMapping("/update")
	public ResponseEntity<GlobalResponseDto<SuccessCode>> saveCharacter(UpdateRequest request) throws IOException {
		return ResponseEntity.status(HttpStatus.OK)
			.body(GlobalResponseDto.success(updateInfoService.execute(request)));
	}

	@Operation(summary = "캐릭터 검색", description = "사용자 캐릭터의 정보를 검색합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/search")
	public ResponseEntity<GlobalResponseDto<CharacterInfoResponse>> SearchCharacter(SearchRequest request) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(GlobalResponseDto.success(searchService.execute(request), SuccessCode.SUCCESS));
	}

	@Operation(summary = "캐릭터 레벨 랭킹", description = "전체 캐릭터의 레벨 순위를 호출합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/ranking/level")
	public ResponseEntity<GlobalResponseDto<List<RankingResponse>>> levelRanking() {
		return ResponseEntity.status(HttpStatus.OK)
			.body(GlobalResponseDto.success(rankService.level(), SuccessCode.SUCCESS));
	}

	@Operation(summary = "캐릭터 유니온 레벨 랭킹", description = "전체 캐릭터의 유니온 레벨 순위를 호출합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/ranking/union")
	public ResponseEntity<GlobalResponseDto<List<RankingResponse>>> unionRanking() {
		return ResponseEntity.status(HttpStatus.OK)
			.body(GlobalResponseDto.success(rankService.unionLevel(), SuccessCode.SUCCESS));
	}

	@Operation(summary = "캐릭터 전투력 랭킹", description = "전체 캐릭터의 전투력 순위를 호출합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/ranking/combat-power")
	public ResponseEntity<GlobalResponseDto<List<RankingResponse>>> combatPowerRanking() {
		return ResponseEntity.status(HttpStatus.OK)
			.body(GlobalResponseDto.success(rankService.combatPower(), SuccessCode.SUCCESS));
	}

	@Operation(summary = "캐릭터 레벨 평균", description = "전체 캐릭터의 레벨 평균을 호출합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/average/level")
	public ResponseEntity<GlobalResponseDto<AverageResponse>> levelAverage() {
		return ResponseEntity.status(HttpStatus.OK)
			.body(GlobalResponseDto.success(averageStatisticsService.level(), SuccessCode.SUCCESS));
	}

	@Operation(summary = "캐릭터 유니온 레벨 평균", description = "전체 캐릭터의 유니온 레벨 평균을 호출합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/average/union")
	public ResponseEntity<GlobalResponseDto<AverageResponse>> unionAverage() {
		return ResponseEntity.status(HttpStatus.OK)
			.body(GlobalResponseDto.success(averageStatisticsService.unionLevel(), SuccessCode.SUCCESS));
	}

	@Operation(summary = "캐릭터 전투력 평균", description = "전체 캐릭터의 전투력 평균을 호출합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/average/combat-power")
	public ResponseEntity<GlobalResponseDto<AverageResponse>> combatPowerAverage() {
		return ResponseEntity.status(HttpStatus.OK)
			.body(GlobalResponseDto.success(averageStatisticsService.combatPower(), SuccessCode.SUCCESS));
	}
}
