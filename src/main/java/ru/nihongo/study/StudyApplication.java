package ru.nihongo.study;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.nihongo.study.service.ai.AiCaller;

@SpringBootApplication(scanBasePackages = "ru.nihongo.study")
public class StudyApplication {
    @Autowired
    AiCaller aiCaller;

    public static void main(String[] args) {
        SpringApplication.run(StudyApplication.class, args);
    }
}
