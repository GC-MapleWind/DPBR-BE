package com.dpbr.dpbrbe.global.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession // Session을 Redis로 관리하기 위한 어노테이션
public class RedisConfig {

	@Value("${spring.data.redis.host}")
	private String host;

	@Value("${spring.data.redis.port}")
	private int port;

	// Redis 연결 설정
	@Bean
	public RedisConnectionFactory redisConnectionFactory(){
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(host);
		redisStandaloneConfiguration.setPort(port);
		return new LettuceConnectionFactory(redisStandaloneConfiguration);
	}

}


