package ru.nihongo.study;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import ru.nihongo.study.service.TranslationService;
import ru.nihongo.study.service.ai.AiCaller;

@SpringBootApplication(scanBasePackages = "ru.nihongo.study")
@EnableFeignClients(basePackages = "ru.nihongo.study.adapter.feign")
public class StudyApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudyApplication.class, args);
    }
}
