package ru.nihongo.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nihongo.study.entity.Deck;

import java.util.List;

public interface DeckRepository extends JpaRepository<Deck, Long> {
    List<Deck> findByUserId(Long userId);
}
