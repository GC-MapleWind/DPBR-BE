package com.dpbr.dpbrbe.global.auth.usecase;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dpbr.dpbrbe.domain.shared.Role;
import com.dpbr.dpbrbe.domain.user.domain.User;
import com.dpbr.dpbrbe.domain.user.domain.repository.UserRepository;
import com.dpbr.dpbrbe.domain.user.exception.UserNotFoundException;
import com.dpbr.dpbrbe.global.auth.domain.AuthDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public AuthDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
		return new AuthDetails(user.getUserId(), user.getEmail(), Role.USER);
	}
}
