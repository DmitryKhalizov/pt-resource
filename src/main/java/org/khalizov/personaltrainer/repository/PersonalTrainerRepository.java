package org.khalizov.personaltrainer.repository;

import org.khalizov.personaltrainer.model.Personal_Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalTrainerRepository extends JpaRepository<Personal_Trainer, Integer> {
}
