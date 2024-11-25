package ru.nihongo.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nihongo.study.entity.Deck;
import ru.nihongo.study.entity.UserInfo;

import java.util.List;

public interface DeckRepository extends JpaRepository<Deck, Long> {
    List<Deck> findByUserInfosContaining(UserInfo userInfo);
}
