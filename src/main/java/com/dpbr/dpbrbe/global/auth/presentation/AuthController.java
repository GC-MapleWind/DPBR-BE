package com.dpbr.dpbrbe.global.auth.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dpbr.dpbrbe.global.auth.presentation.dto.response.AuthResponse;
import com.dpbr.dpbrbe.global.auth.usecase.AuthService;
import com.dpbr.dpbrbe.global.error.ErrorResponse;
import com.dpbr.dpbrbe.global.jwt.JwtProvider;
import com.dpbr.dpbrbe.global.jwt.dto.request.RefreshRequest;
import com.dpbr.dpbrbe.global.jwt.dto.response.JwtResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final JwtProvider jwtProvider;

	@Operation(summary = "구글 로그인", description = "토큰을 통해 사용자를 로그인합니다.")
	@ApiResponses({
	        @ApiResponse(responseCode = "201"),
	        @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
	        @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
	        @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
	        @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestParam(value = "code") String code) {
	    return ResponseEntity.ok(authService.getUserInfo(code));
	}

	@Operation(summary = "토큰 갱신", description = "사용자 계정의 액세스토큰을 갱신합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "201"),
		@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping("/refresh")
	public ResponseEntity<JwtResponse> refresh(@RequestBody RefreshRequest request) {
		return ResponseEntity.ok(jwtProvider.refreshAccessToken(request.refreshToken()));
	}
}
