package org.khalizov.personaltrainer.mapper;

import org.khalizov.personaltrainer.dto.TrainerReviewDTO;
import org.khalizov.personaltrainer.model.TrainerReview;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TrainerReviewDTOMapper implements Function<TrainerReview, TrainerReviewDTO> {

    @Override
    public TrainerReviewDTO apply(TrainerReview review) {
        return new TrainerReviewDTO(
                review.getReviewId(),
                review.getUser().getUserId(),
                review.getTrainer().getTrainerId(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt()
        );
    }
}
