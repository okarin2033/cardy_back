package ru.nihongo.study.adapter.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nihongo.study.adapter.controller.v1.dto.TranslationRequestDto;
import ru.nihongo.study.service.TranslationService;

import java.util.List;

@RestController
@RequestMapping("/v1/translate")
@RequiredArgsConstructor
public class TranslationController {

    private final TranslationService translationService;

    @PostMapping
    public List<String> translate(@RequestBody TranslationRequestDto request) {
        return translationService.translate(
            request.getText(),
            request.getSourceLanguage().getDeeplCode(),
            request.getTargetLanguage().getDeeplCode()
        );
    }
}
