package com.dpbr.dpbrbe.global.openAPI.usecase;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.dpbr.dpbrbe.global.openAPI.dto.response.CharacterBasicInfoResponse;
import com.dpbr.dpbrbe.domain.character.presentation.dto.response.InfoResponse;
import com.dpbr.dpbrbe.global.openAPI.dto.response.CharacterStatInfoResponse;
import com.dpbr.dpbrbe.global.openAPI.dto.response.CharacterUnionInfoResponse;
import com.dpbr.dpbrbe.global.openAPI.Connection;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FetchCharacterInfo {

    private final ObjectMapper objectMapper;
    private final Connection connection;

    public InfoResponse execute(String ocid) throws IOException {
        CharacterBasicInfoResponse basicInfo = fetchCharacterData("/character/basic?ocid=" + ocid, CharacterBasicInfoResponse.class);
        CharacterUnionInfoResponse unionInfo = fetchCharacterData("/user/union?ocid=" + ocid, CharacterUnionInfoResponse.class);
        CharacterStatInfoResponse statInfo = fetchCharacterData("/character/stat?ocid=" + ocid, CharacterStatInfoResponse.class);

        return InfoResponse.of(basicInfo, unionInfo, statInfo);
    }

    private <T> T fetchCharacterData(String path, Class<T> responseType) throws IOException {
        String response = connection.execute(path);
        return objectMapper.readValue(response, responseType);
    }
}
