package ru.nihongo.study.service.cache;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TranslationCacheEntry {
    private final String translation;
    private final LocalDateTime createdAt;
    private final String language;
    
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(createdAt.plusHours(24));
    }
}
