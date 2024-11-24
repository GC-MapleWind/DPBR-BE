package com.dpbr.dpbrbe.domain.user.usecase;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dpbr.dpbrbe.domain.user.domain.User;
import com.dpbr.dpbrbe.domain.user.domain.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StatisticsService {

	private final UserRepository userRepository;

	public Map<String, Integer> major() {
		List<User> users = userRepository.findAll(); // 사용자 전체 조회

		// 전공 별 사용자 수 집계
		return users.stream()
			.collect(Collectors.toMap(
				User::getMajor,
				major -> 1,
				Integer::sum
			));
	}
}
