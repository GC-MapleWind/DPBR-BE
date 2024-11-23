package com.dpbr.dpbrbe.domain.user.usecase;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.dpbr.dpbrbe.domain.character.domain.Character;
import com.dpbr.dpbrbe.domain.character.domain.repository.CharacterRepository;
import com.dpbr.dpbrbe.domain.character.exception.CharacterNotFoundException;
import com.dpbr.dpbrbe.domain.character.presentation.dto.response.InfoResponse;
import com.dpbr.dpbrbe.domain.user.domain.User;
import com.dpbr.dpbrbe.domain.user.domain.repository.UserRepository;
import com.dpbr.dpbrbe.domain.user.exception.UserCharacterNotFoundException;
import com.dpbr.dpbrbe.domain.user.exception.UserNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FindUserCharacter {

	private final UserRepository userRepository;
	private final CharacterRepository characterRepository;

	public InfoResponse execute(UserDetails userDetails) {
		User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);

		String ocid = user.getOcid();

		if(ocid == null) {
			throw new UserCharacterNotFoundException();
		}

		Character character = characterRepository.findById(ocid).orElseThrow(CharacterNotFoundException::new);

		return InfoResponse.from(character);
	}
}
