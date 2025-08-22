package org.khalizov.personaltrainer.repository;

import org.khalizov.personaltrainer.model.TrainerSport;
import org.khalizov.personaltrainer.model.TrainerSportId;
import org.khalizov.personaltrainer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerSportRepository extends JpaRepository<TrainerSport, TrainerSportId> {
}
