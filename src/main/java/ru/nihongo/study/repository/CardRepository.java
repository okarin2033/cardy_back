package ru.nihongo.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nihongo.study.entity.Card;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByDeckId(Long deckId);
}
