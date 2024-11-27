package ru.nihongo.study.service.cache;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TranslationCacheService {
    private final Map<String, TranslationCacheEntry> cache = new ConcurrentHashMap<>();
    
    public String getTranslation(String word, String language) {
        String key = generateKey(word, language);
        TranslationCacheEntry entry = cache.get(key);
        
        if (entry != null && !entry.isExpired()) {
            return entry.getTranslation();
        }
        
        return null;
    }
    
    public void cacheTranslation(String word, String language, String translation) {
        String key = generateKey(word, language);
        cache.put(key, new TranslationCacheEntry(translation, LocalDateTime.now(), language));
    }
    
    private String generateKey(String word, String language) {
        return word.toLowerCase() + ":" + language.toLowerCase();
    }
}
