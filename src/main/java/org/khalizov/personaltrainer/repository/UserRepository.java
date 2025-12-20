package org.khalizov.personaltrainer.repository;

import org.khalizov.personaltrainer.model.Status;
import org.khalizov.personaltrainer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByNickname(String nickname);

    List<User> findByStatus(Status status);

    Optional<User> findByEmail(String email);
}
