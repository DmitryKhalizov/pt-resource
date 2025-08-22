package org.khalizov.personaltrainer.dto;

import java.time.LocalDateTime;

public class TrainerReviewDTO {
    private Integer reviewId;
    private Integer userId;
    private Integer trainerId;
    private Integer rating;
    private String text;
    private LocalDateTime createdAt;

}
