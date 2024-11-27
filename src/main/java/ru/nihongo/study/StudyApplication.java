package ru.nihongo.study;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.nihongo.study.service.AIService;
import ru.nihongo.study.service.ai.modules.AiCaller;

@SpringBootApplication(scanBasePackages = "ru.nihongo.study")
public class StudyApplication {
    @Autowired
    AiCaller aiCaller;

    public static void main(String[] args) {
        SpringApplication.run(StudyApplication.class, args);
    }
}
