package ru.nihongo.study.adapter.controller.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nihongo.study.entity.enumeration.Language;
import ru.nihongo.study.service.TranslationService;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class TranslationController {

    private final TranslationService translationService;

    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @GetMapping("/translate")
    public List<String> translate(
        @RequestParam Language sourceLang,
        @RequestParam Language targetLang,
        @RequestParam String text) {

        String sourceCode = sourceLang.getDeeplCode();
        String targetCode = targetLang.getDeeplCode();

        return translationService.translate(text, sourceCode, targetCode);
    }
}