package com.dpbr.dpbrbe.domain.user.usecase;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.dpbr.dpbrbe.domain.character.domain.Character;
import com.dpbr.dpbrbe.domain.character.domain.repository.CharacterRepository;
import com.dpbr.dpbrbe.domain.character.exception.CharacterNotFoundException;
import com.dpbr.dpbrbe.domain.character.presentation.dto.response.CharacterInfoResponse;
import com.dpbr.dpbrbe.domain.user.domain.User;
import com.dpbr.dpbrbe.domain.user.domain.repository.UserRepository;
import com.dpbr.dpbrbe.domain.user.exception.UserCharacterNotFoundException;
import com.dpbr.dpbrbe.domain.user.exception.UserNotFoundException;
import com.dpbr.dpbrbe.domain.user.presentation.dto.response.UserInfoResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserInfoService {

	private final UserRepository userRepository;
	private final CharacterRepository characterRepository;

	public UserInfoResponse execute(UserDetails userDetails) {
		// 토큰을 이용하여 사용자 조회
		User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);

		// 사용자의 캐릭터 id 조회
		String ocid = user.getOcid();

		// 캐릭터의 id가 없으면 예외 발생
		if(ocid == null) {
			throw new UserCharacterNotFoundException();
		}

		// 캐릭터 id로 캐릭터 조회
		Character character = characterRepository.findById(ocid).orElseThrow(CharacterNotFoundException::new);

		// 응답 생성
		return UserInfoResponse.of(user, character);
	}
}
