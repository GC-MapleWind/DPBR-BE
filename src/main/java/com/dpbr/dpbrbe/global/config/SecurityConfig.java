package com.dpbr.dpbrbe.global.config;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import com.dpbr.dpbrbe.global.auth.handler.CustomOAuth2SuccessHandler;
import com.dpbr.dpbrbe.global.error.exception.CustomAccessDeniedHandler;
import com.dpbr.dpbrbe.global.filter.ExceptionHandleFilter;
import com.dpbr.dpbrbe.global.jwt.JwtAuthenticationEntryPoint;
import com.dpbr.dpbrbe.global.jwt.JwtProvider;
import com.dpbr.dpbrbe.global.jwt.TokenAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtProvider tokenProvider;
	private final JwtAuthenticationEntryPoint authenticationEntryPoint;
	private final CustomAccessDeniedHandler accessDeniedHandler;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.httpBasic(AbstractHttpConfigurer::disable)
			.cors((cors) -> cors
				.configurationSource(corsConfigurationSource())
			)
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.sessionManagement(
				(sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
			.oauth2Login(oauth2 -> oauth2.successHandler(new CustomOAuth2SuccessHandler()));

		http
			.authorizeHttpRequests((authorize) ->
				authorize
					.requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll() // API 명세서
					.requestMatchers("/login/**").permitAll() // 구글 로그인
					.requestMatchers("/v1/user/**").permitAll() // 사용자
					.requestMatchers("/v1/character/**").permitAll() // 캐릭터
					.anyRequest().permitAll()
			);

		http
			.exceptionHandling(exceptionHandlingCustomizer ->
				exceptionHandlingCustomizer
					.authenticationEntryPoint(authenticationEntryPoint)
					.accessDeniedHandler(accessDeniedHandler)
			);

		http
			.addFilterBefore(new TokenAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new ExceptionHandleFilter(), TokenAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList(
			"http://localhost:8080",
			"http://localhost:3000",
			"http://mapletest.kro.kr:8080",
			"http://mapletest.kro.kr:3000"));
		configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(List.of("*"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
