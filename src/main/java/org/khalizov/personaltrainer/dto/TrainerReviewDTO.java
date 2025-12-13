package org.khalizov.personaltrainer.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Trainer review DTO")
public class TrainerReviewDTO {
    private Integer reviewId;
    private Integer userId;
    private Integer trainerId;
    private Integer rating;
    private String content;
    private LocalDateTime createdAt;
}
