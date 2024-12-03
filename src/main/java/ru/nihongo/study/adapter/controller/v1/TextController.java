package ru.nihongo.study.adapter.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nihongo.study.adapter.controller.v1.dto.text.CreateTextDto;
import ru.nihongo.study.adapter.controller.v1.dto.text.TextDto;
import ru.nihongo.study.adapter.controller.v1.dto.text.TextListDto;
import ru.nihongo.study.adapter.controller.v1.mappers.TextMapper;
import ru.nihongo.study.entity.Text;
import ru.nihongo.study.service.TextService;

import java.util.List;

@RestController
@RequestMapping("v1/texts")
@RequiredArgsConstructor
public class TextController {
    private final TextService textService;
    private final TextMapper textMapper;

    @PostMapping
    public ResponseEntity<TextDto> createText(@RequestBody CreateTextDto createTextDto) {
        Text text = textMapper.mapToEntity(createTextDto);
        Text createdText = textService.createText(text);
        return ResponseEntity.ok(textMapper.mapToDto(createdText));
    }

    @PutMapping("/{textId}")
    public ResponseEntity<TextDto> updateText(@PathVariable Long textId, @RequestBody CreateTextDto updateTextDto) {
        Text text = textMapper.mapToEntity(updateTextDto);
        Text updatedText = textService.updateText(textId, text);
        return ResponseEntity.ok(textMapper.mapToDto(updatedText));
    }

    @DeleteMapping("/{textId}")
    public ResponseEntity<Void> deleteText(@PathVariable Long textId) {
        textService.deleteText(textId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<TextListDto>> getUserTexts() {
        List<Text> texts = textService.getUserTexts();
        List<TextListDto> textDtos = texts.stream()
                .map(textMapper::mapToListDto)
                .toList();
        return ResponseEntity.ok(textDtos);
    }

    @GetMapping("/{textId}")
    public ResponseEntity<TextDto> getTextById(@PathVariable Long textId) {
        Text text = textService.getTextById(textId);
        return ResponseEntity.ok(textMapper.mapToDto(text));
    }
}
