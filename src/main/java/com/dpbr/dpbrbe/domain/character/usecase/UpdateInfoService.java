package com.dpbr.dpbrbe.domain.character.usecase;

import static com.dpbr.dpbrbe.global.success.SuccessCode.*;

import java.io.IOException;

import org.springframework.scheduling.annotation.Scheduled;
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
		// 캐릭터 정보 조회
		Character character = characterRepository.findByName(request.name())
			.orElseThrow(CharacterNotFoundException::new);
		// NEXON OPEN API를 이용하여 갱신된 정보 호출
		character.updateInfo(fetchCharacterInfo.execute(character.getOcid()));

		// 갱신된 정보 저장
		characterRepository.save(character);

		return UPDATE_CHARACTER_SUCCESS;
	}

	// 매일 새벽 3시에 실행
	@Scheduled(cron = "0 0 3 * * ?")
	public void executeDailyTask() {
		// 모든 캐릭터 정보 갱신
		characterRepository.findAll().parallelStream().forEach(character -> {
			try {
				character.updateInfo(fetchCharacterInfo.execute(character.getOcid()));
				characterRepository.save(character);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
