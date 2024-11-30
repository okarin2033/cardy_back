package ru.nihongo.study.controller.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nihongo.study.controller.v1.dto.EnumDto;
import ru.nihongo.study.entity.enumeration.Language;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/api/enums")
public class EnumController {

    @GetMapping("/languages")
    public ResponseEntity<List<EnumDto<String>>> getLanguages() {
        List<EnumDto<String>> languages = Arrays.stream(Language.values())
                .map(lang -> new EnumDto<>(lang.name(), lang.getValue()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(languages);
    }
}
