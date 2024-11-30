package ru.nihongo.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nihongo.study.entity.Text;
import ru.nihongo.study.entity.UserInfo;

import java.util.List;

public interface TextRepository extends JpaRepository<Text, Long> {
    List<Text> findByUsersContaining(UserInfo user);
}
