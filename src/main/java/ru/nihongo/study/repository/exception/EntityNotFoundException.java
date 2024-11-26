package ru.nihongo.study.repository.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EntityNotFoundException extends RuntimeException {
    private String className;
    private Long id;

    public EntityNotFoundException(String className, Long id) {
        this.className = className;
        this.id = id;
    }
}
