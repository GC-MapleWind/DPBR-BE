package com.dpbr.dpbrbe.global.openAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dpbr.dpbrbe.global.openAPI.exception.FailToConnectNexonOpenAPIException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Connection {

	@Value("${api-key}")
	private String API_KEY;

	private static final String API_URL = "https://open.api.nexon.com/maplestory/v1";

	public String execute(String path) {
		StringBuilder response = new StringBuilder();

		try {
			// 요청을 보낼 URL
			URL url = new URL(API_URL + path);
			// URL 연결 객체 생성
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();

			// header 설정
			connection.setRequestMethod("GET"); // 요청 방식 설정
			connection.setRequestProperty("x-nxopen-api-key", API_KEY); // API 키 설정

			BufferedReader in;
			// 요청이 성공하면 응답 반환
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}

				in.close();
			// 요청이 실패하면 예외 처리 및 에러 로깅
			} else {
				JsonObject jsonObject = JsonParser.parseString(
						new BufferedReader(new InputStreamReader(connection.getErrorStream())).readLine())
					.getAsJsonObject();

				JsonObject errorObject = jsonObject.getAsJsonObject("error");
				String errorName = errorObject.get("name").getAsString();
				String errorMessage = errorObject.get("message").getAsString();

				log.info(url.toString());
				log.error("Error name: {}, Error message: {}", errorName, errorMessage);
				throw new FailToConnectNexonOpenAPIException();
			}

		} catch (IOException e) {
			throw new FailToConnectNexonOpenAPIException();
		}

		return response.toString();
	}
}
