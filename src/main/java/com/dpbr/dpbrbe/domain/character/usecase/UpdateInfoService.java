package com.dpbr.dpbrbe.domain.character.usecase;

import static com.dpbr.dpbrbe.global.success.SuccessCode.*;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.dpbr.dpbrbe.domain.character.domain.Character;
import com.dpbr.dpbrbe.domain.character.domain.repository.CharacterRepository;
import com.dpbr.dpbrbe.domain.character.exception.CharacterNotFoundException;
import com.dpbr.dpbrbe.domain.character.presentation.dto.request.UpdateRequest;
import com.dpbr.dpbrbe.global.openAPI.usecase.FetchCharacterInfo;
import com.dpbr.dpbrbe.global.success.SuccessCode;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UpdateInfoService {

	private final FetchCharacterInfo fetchCharacterInfo;
	private final CharacterRepository characterRepository;

	public SuccessCode execute(UpdateRequest request) throws IOException {
		Character character = characterRepository.findByName(request.name()).orElseThrow(CharacterNotFoundException::new);
		character.updateInfo(fetchCharacterInfo.execute(character.getOcid()));

		characterRepository.save(character);

		return UPDATE_CHARACTER_SUCCESS;
	}
}
