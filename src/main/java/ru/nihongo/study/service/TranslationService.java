package ru.nihongo.study.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.nihongo.study.adapter.feign.DeepLClient;
import ru.nihongo.study.adapter.feign.dto.TranslationResponse;

import java.util.List;

@Service
public class TranslationService {

    private final DeepLClient deepLClient;
    private final String apiKey;

    public TranslationService(DeepLClient deepLClient, @Value("${deepl.api.key}") String apiKey) {
        this.deepLClient = deepLClient;
        this.apiKey = apiKey;
    }

    public List<String> translate(String text, String sourceLang, String targetLang) {
        String authHeader = "DeepL-Auth-Key " + apiKey;
        TranslationResponse response = deepLClient.translate(authHeader, text, sourceLang, targetLang);
        return response.getTranslations().stream()
            .map(TranslationResponse.Translation::getText)
            .toList();
    }
}