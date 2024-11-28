package ru.nihongo.study.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nihongo.study.service.ai.AiCaller;

@Data
@Component
@RequiredArgsConstructor
public class AIService {
    private final AiCaller caller;

    private static final String REQUEST_MESSAGE = """
        Расскажи о слове %s в %s языке.
        """;

    private static final String WORD_INSTRUCTION = """
        Опиши представленный кандзи/слово и расскажи про особенности его использования в %s языке.
        Раздели ответ на 3 части:
        1) Перевод слова (если есть несколько значений - укажи их)
        2) Разбор слова по составляющим (в случае японского/китайского языка - рассказать про используемые кандзи.)
        3) Пример использования слова в предложениях. Для каждого предложения напиши его перевод.
        """;

    public String getWordResponse(String word, String language) {
        return caller.sendOpenAiMessage(String.format(WORD_INSTRUCTION, language),
            String.format(REQUEST_MESSAGE, word, language));
    }
}
