package ru.nihongo.study.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nihongo.study.entity.Text;
import ru.nihongo.study.service.ai.AiCaller;

@Data
@Component
@RequiredArgsConstructor
public class AIService {
    private final AiCaller caller;
    private final TextService textService;

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

    private static final String TRANSLATE_INSTRUCTION = """
        You are a professional translator. Translate the following text from %s to %s. Maintain the original meaning and style.
        """;
    private static final String EXPLAIN_INSTRUCTION = """
        Explain the meaning and context of the following sentence from %s to %s. Consider cultural nuances if present.
        Consider text context. Text: %s
        """;

    public String getWordResponse(String word, String language) {
        return caller.sendOpenAiMessage(String.format(WORD_INSTRUCTION, language),
            String.format(REQUEST_MESSAGE, word, language));
    }

    public String translateText(Long textId, String targetLanguage) {
        Text text = getTextById(textId);

        String instruction = String.format(TRANSLATE_INSTRUCTION,
            text.getLanguage().toString().toLowerCase(),
            targetLanguage.toLowerCase());

        return caller.sendOpenAiMessage(instruction, text.getContent());
    }

    public String explainSentence(Long textId, String sentence, String targetLanguage) {
        Text text = getTextById(textId);

        return caller.sendOpenAiMessage(String.format(EXPLAIN_INSTRUCTION, text.getLanguage().toString(), targetLanguage, text.getContent()),
            sentence);
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

    public Text getTextById(Long textId) {
        return textService.getTextById(textId);
    }
}
