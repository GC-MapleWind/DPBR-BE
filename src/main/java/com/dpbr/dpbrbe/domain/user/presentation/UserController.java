package com.dpbr.dpbrbe.domain.user.presentation;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dpbr.dpbrbe.domain.user.presentation.dto.request.CharacterRequest;
import com.dpbr.dpbrbe.domain.user.usecase.SaveCharacter;
import com.dpbr.dpbrbe.domain.user.usecase.StatisticsService;
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
@RequestMapping("v1/user")
@RequiredArgsConstructor
public class UserController {

	private final SaveCharacter saveCharacter;
	private final StatisticsService statisticsService;

	@Operation(summary = "캐릭터 정보 저장", description = "사용자 캐릭터의 정보를 저장합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "201"),
		@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping("/save-character")
	public ResponseEntity<GlobalResponseDto<SuccessCode>> saveCharacter(
		@AuthenticationPrincipal UserDetails userDetails,
		@RequestBody CharacterRequest request) throws IOException {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(GlobalResponseDto.success(saveCharacter.execute(userDetails, request)));
	}

	@Operation(summary = "학과 통계", description = "사용자들의 학과 통계를 산출합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping("/statistics/major")
	public ResponseEntity<GlobalResponseDto<Map<String, Integer>>> statisticsMajor() throws IOException {
		return ResponseEntity.status(HttpStatus.OK)
			.body(GlobalResponseDto.success(statisticsService.major()));
	}
}
