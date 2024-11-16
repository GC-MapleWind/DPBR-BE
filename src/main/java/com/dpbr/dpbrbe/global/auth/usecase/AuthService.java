package com.dpbr.dpbrbe.global.auth.usecase;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.dpbr.dpbrbe.domain.user.domain.User;
import com.dpbr.dpbrbe.domain.user.domain.repository.UserRepository;
import com.dpbr.dpbrbe.global.auth.domain.GoogleAccessToken;
import com.dpbr.dpbrbe.global.auth.domain.GoogleProfile;
import com.dpbr.dpbrbe.global.auth.presentation.dto.response.AuthResponse;
import com.dpbr.dpbrbe.global.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private static final String TOKEN_URI = "https://oauth2.googleapis.com/token";
	private static final String USER_INFO_URI = "https://www.googleapis.com/oauth2/v2/userinfo";

	private final UserRepository userRepository;
	private final JwtProvider jwtProvider;

	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String clientId;

	@Value("${spring.security.oauth2.client.registration.google.client-secret}")
	private String clientSecret;

	@Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
	private String redirectUri;

	@Transactional
	public AuthResponse getUserInfo(String code) {
		String token = getToken(code);
		return getUserInfoFromToken(token);
	}

	private String getToken(String code) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		String decode = URLDecoder.decode(code, StandardCharsets.UTF_8);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", clientId);
		body.add("redirect_uri", redirectUri);
		body.add("code", decode);
		body.add("client_secret", clientSecret);

		HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(body, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(TOKEN_URI, tokenRequest, String.class);

		return GoogleAccessToken.from(response.getBody()).accessToken();
	}

	private AuthResponse getUserInfoFromToken(String token) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		headers.add("Authorization", "Bearer " + token);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		HttpEntity<Void> profileRequest = new HttpEntity<>(headers);

		ResponseEntity<String> response = restTemplate.exchange(USER_INFO_URI, HttpMethod.GET, profileRequest,
			String.class);

		User user = getUser(GoogleProfile.from(response.getBody()));

		return createToken(user);
	}

	private User getUser(GoogleProfile googleProfile) {
		String email = googleProfile.email();
		String name = googleProfile.name();

		return userRepository.findByEmail(email).orElseGet(() -> userRepository.save(User.create(email, name)));
	}

	private AuthResponse createToken(User user) {
		String accessToken = jwtProvider.generateAccessToken(user.getUserId(), user.getEmail(), user.getRole());
		String refreshToken = generateRefreshToken(user);

		userRepository.save(user);

		return new AuthResponse(accessToken, refreshToken, user.getName());
	}

	private String generateRefreshToken(User user) {
		String refreshToken = user.getRefreshToken();
		if (refreshToken == null || jwtProvider.isInvalidToken(refreshToken)) {
			refreshToken = jwtProvider.generateRefreshToken(user.getUserId(), user.getEmail(), user.getRole());
			updateRefreshToken(user, refreshToken);
		}
		return refreshToken;
	}

	private void updateRefreshToken(User user, String refreshToken) {
		user.updateRefreshToken(refreshToken);
		userRepository.save(user);
	}
}
