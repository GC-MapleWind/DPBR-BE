package com.dpbr.dpbrbe.global.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.filter.OncePerRequestFilter;

import com.dpbr.dpbrbe.global.error.ErrorCode;
import com.dpbr.dpbrbe.global.error.ErrorResponse;
import com.dpbr.dpbrbe.global.error.exception.ServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ExceptionHandleFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (ServiceException e) {
			sendErrorResponse(response, e.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			sendErrorResponse(response, ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}

	private void sendErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
		response.setStatus(errorCode.getHttpStatus());
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		ObjectMapper objectMapper = new ObjectMapper();
		ErrorResponse errorResponse = ErrorResponse.of(errorCode);
		Map<String, ErrorResponse> result = new HashMap<>();
		result.put("result", errorResponse);
		response.getWriter().write(objectMapper.writeValueAsString(result));
	}
}
