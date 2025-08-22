package org.khalizov.personaltrainer.repository;

import org.khalizov.personaltrainer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Integer> {
}
