package com.teamproject.furniture.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RootConfig {

    @Bean
    public ModelMapper getMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()  // configuration 객체에 접근
                .setFieldMatchingEnabled(true)  // 필드 매칭을 활성화하는 옵션
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)  // 접근레벨 private로
                .setMatchingStrategy(MatchingStrategies.STRICT);    // 필드 이름과 타입이 완전히 일치하는 경우에만 매핑

        return modelMapper;
    }
}
