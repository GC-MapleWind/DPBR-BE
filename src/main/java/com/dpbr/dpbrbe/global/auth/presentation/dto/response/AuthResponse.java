package com.dpbr.dpbrbe.global.auth.presentation.dto.response;

public record AuthResponse(String accessToken,
						   String refreshToken,
						   String username) {
}
