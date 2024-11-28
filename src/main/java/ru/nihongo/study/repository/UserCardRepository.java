package ru.nihongo.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nihongo.study.entity.UserCard;
import ru.nihongo.study.entity.UserCardId;
import ru.nihongo.study.entity.UserInfo;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserCardRepository extends JpaRepository<UserCard, UserCardId> {
    @Query("SELECT COUNT(c) FROM UserCard c WHERE c.nextReview < :localDateTime " +
        "AND c.card.deck.id = :cardDeckId " +
        "AND c.user = :user " +
        "AND c.isNew = :isNew")
    long countCardsToReview(
        @Param("localDateTime") LocalDateTime localDateTime,
        @Param("cardDeckId") Long cardDeckId,
        @Param("user") UserInfo user,
        @Param("isNew") Boolean isNew
    );

    long countByUserAndCardDeckId(UserInfo user, Long deckId);

    List<UserCard> findByUserAndCardDeckId(UserInfo user, Long deckId);
}
