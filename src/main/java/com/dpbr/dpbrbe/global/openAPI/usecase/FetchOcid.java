package com.dpbr.dpbrbe.global.openAPI.usecase;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;

import com.dpbr.dpbrbe.global.openAPI.dto.response.CharacterOcidResponse;
import com.dpbr.dpbrbe.global.openAPI.Connection;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FetchOcid {
    private final Connection connection;
    private final ObjectMapper objectMapper;

    public String execute(String characterName) throws IOException {
        String path = "/id?character_name=" + URLEncoder.encode(characterName, StandardCharsets.UTF_8);

		return objectMapper.readValue(connection.execute(path), CharacterOcidResponse.class).ocid();
    }
}
