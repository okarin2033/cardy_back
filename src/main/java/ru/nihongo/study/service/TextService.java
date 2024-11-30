package ru.nihongo.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nihongo.study.entity.Text;
import ru.nihongo.study.entity.UserInfo;
import ru.nihongo.study.repository.TextRepository;
import ru.nihongo.study.service.ai.AiCaller;
import ru.nihongo.study.service.utils.SecurityUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TextService {
    private final TextRepository textRepository;
    private final AiCaller aiCaller;

    public Text createText(Text text) {
        UserInfo creator = SecurityUtil.getcurrentUserInfo();
        text.getUsers().add(creator);
        return textRepository.save(text);
    }

    public Text updateText(Long textId, Text updatedText) {
        Text text = getTextById(textId);
        text.setTitle(updatedText.getTitle());
        text.setContent(updatedText.getContent());
        text.setLanguage(updatedText.getLanguage());
        return textRepository.save(text);
    }

    public void deleteText(Long textId) {
        textRepository.deleteById(textId);
    }

    public List<Text> getUserTexts() {
        UserInfo currentUser = SecurityUtil.getcurrentUserInfo();
        return textRepository.findByUsersContaining(currentUser);
    }

    public Text getTextById(Long textId) {
        return textRepository.findById(textId)
                .orElseThrow(() -> new RuntimeException("Text not found"));
    }
}
