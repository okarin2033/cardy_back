package ru.nihongo.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nihongo.study.entity.UserCard;
import ru.nihongo.study.entity.UserCardId;

@Repository
public interface UserCardRepository extends JpaRepository<UserCard, UserCardId> {
}