package org.khalizov.personaltrainer.repository;

import org.khalizov.personaltrainer.model.TrainerReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainerReviewRepository extends JpaRepository<TrainerReview, Integer> {
    List<TrainerReview> findByTrainer_TrainerId(Integer trainerId);
}
