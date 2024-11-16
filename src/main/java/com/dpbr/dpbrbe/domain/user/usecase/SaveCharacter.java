package com.dpbr.dpbrbe.domain.user.usecase;

import static com.dpbr.dpbrbe.global.success.SuccessCode.*;

import java.io.IOException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.dpbr.dpbrbe.domain.character.domain.Character;
import com.dpbr.dpbrbe.domain.user.presentation.dto.request.CharacterRequest;
import com.dpbr.dpbrbe.domain.character.domain.repository.CharacterRepository;
import com.dpbr.dpbrbe.domain.user.presentation.dto.response.CharacterInfoResponse;
import com.dpbr.dpbrbe.domain.user.domain.User;
import com.dpbr.dpbrbe.domain.user.domain.repository.UserRepository;
import com.dpbr.dpbrbe.domain.user.exception.UserNotFoundException;
import com.dpbr.dpbrbe.global.openAPI.usecase.FetchCharacterInfo;
import com.dpbr.dpbrbe.global.openAPI.usecase.FetchOcid;
import com.dpbr.dpbrbe.global.success.SuccessCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaveCharacter {

	private final FetchOcid fetchOcid;
	private final UserRepository userRepository;
	private final CharacterRepository characterRepository;
	private final FetchCharacterInfo fetchCharacterInfo;

	public SuccessCode execute(UserDetails userDetails, CharacterRequest request) throws IOException {
		User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);

		String ocid = fetchOcid.execute(request.name());

		CharacterInfoResponse characterInfo = fetchCharacterInfo.execute(ocid);

		characterRepository.save(Character.of(ocid, request.name(), characterInfo));

		user.updateOcid(ocid);

		return SAVE_CHARACTER_SUCCESS;
	}
}

