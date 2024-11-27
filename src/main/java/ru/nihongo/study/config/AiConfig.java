package ru.nihongo.study.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.nihongo.study.service.ai.modules.AiCaller;

@Configuration
public class AiConfig {
    @Value("${api.base_url}")
    private String API_URL;
    @Value("${api.key}")
    private String API_KEY;

    @Bean
    public AiCaller getAiCaller() {
        return new AiCaller(API_KEY, API_URL, "caramelldansen-1");
    }
}
