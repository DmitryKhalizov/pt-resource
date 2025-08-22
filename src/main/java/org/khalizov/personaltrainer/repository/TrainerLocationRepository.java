package org.khalizov.personaltrainer.repository;

import org.khalizov.personaltrainer.model.TrainerLocation;
import org.khalizov.personaltrainer.model.TrainerLocationId;
import org.khalizov.personaltrainer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerLocationRepository extends JpaRepository<TrainerLocation, TrainerLocationId> {
}
