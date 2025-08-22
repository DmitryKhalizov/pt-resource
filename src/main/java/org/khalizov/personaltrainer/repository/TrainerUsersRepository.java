package org.khalizov.personaltrainer.repository;

import org.khalizov.personaltrainer.model.TrainerUsers;
import org.khalizov.personaltrainer.model.TrainerUsersId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerUsersRepository extends JpaRepository<TrainerUsers, TrainerUsersId> {
}
