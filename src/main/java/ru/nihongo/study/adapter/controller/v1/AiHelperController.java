package ru.nihongo.study.adapter.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nihongo.study.service.AIService;
import ru.nihongo.study.service.cache.TranslationCacheService;

@RestController
@RequestMapping("/v1/api/ai")
@RequiredArgsConstructor
public class AiHelperController {
    private final AIService aiService;
    private final TranslationCacheService cacheService;

    @GetMapping("/translate")
    public String getTranslation(@RequestParam String word, @RequestParam String language) {
        // Проверяем кэш
        String cachedTranslation = cacheService.getTranslation(word, language);
        if (cachedTranslation != null) {
            return cachedTranslation;
        }

        String translation = aiService.getWordResponse(word, language);

        cacheService.cacheTranslation(word, language, translation);

        return translation;
    }

    @GetMapping("/text/{textId}/translate")
    public ResponseEntity<String> translateText(
        @PathVariable Long textId,
        @RequestParam String targetLanguage) {
        return ResponseEntity.ok(aiService.translateText(textId, targetLanguage));
    }

    @GetMapping("/text/{textId}/explain")
    public ResponseEntity<String> explainSentence(@PathVariable Long textId, @RequestParam(value = "sentence") String sentence,
        @RequestParam String targetLanguage) {
        return ResponseEntity.ok(aiService.explainSentence(textId, sentence, targetLanguage));
    }
}
