package ru.nihongo.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nihongo.study.entity.UserCard;
import ru.nihongo.study.entity.UserCardId;
import ru.nihongo.study.entity.UserInfo;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserCardRepository extends JpaRepository<UserCard, UserCardId> {
    long countAllByNextReviewBeforeAndCardDeckIdAndUser(LocalDateTime localDateTime, Long cardDeckId, UserInfo user);

    long countByUserAndCardDeckId(UserInfo user, Long deckId);

    List<UserCard> findByUserAndCardDeckId(UserInfo user, Long deckId);
}
