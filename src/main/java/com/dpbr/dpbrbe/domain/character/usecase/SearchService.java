package com.dpbr.dpbrbe.domain.character.usecase;

import org.springframework.stereotype.Service;

import com.dpbr.dpbrbe.domain.character.domain.Character;
import com.dpbr.dpbrbe.domain.character.domain.repository.CharacterRepository;
import com.dpbr.dpbrbe.domain.character.exception.CharacterNotFoundException;
import com.dpbr.dpbrbe.domain.character.presentation.dto.request.SearchRequest;
import com.dpbr.dpbrbe.domain.character.presentation.dto.response.InfoResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SearchService {

	private final CharacterRepository characterRepository;

	public InfoResponse execute(SearchRequest request) {
		Character character = characterRepository.findByName(request.name()).orElseThrow(CharacterNotFoundException::new);
		return InfoResponse.from(character);
	}
}
