package ru.nihongo.study.adapter.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nihongo.study.adapter.feign.dto.TranslationResponse;

@FeignClient(name = "deeplClient", url = "https://api-free.deepl.com/v2")
public interface DeepLClient {

    @PostMapping("/translate")
    TranslationResponse translate(
        @RequestHeader("Authorization") String authHeader,
        @RequestParam("text") String text,
        @RequestParam("source_lang") String sourceLang,
        @RequestParam("target_lang") String targetLang
    );
}