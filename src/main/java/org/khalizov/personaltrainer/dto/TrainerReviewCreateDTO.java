package org.khalizov.personaltrainer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Create trainer review request")
public class TrainerReviewCreateDTO {

    @NotNull
    @Min(1)
    @Max(5)
    @Schema(description = "Rating from 1 to 5", example = "5")
    private Integer rating;

    @NotBlank
    @Size(min = 3, max = 2000)
    @Schema(description = "Review content", example = "Great trainer!")
    private String content;
}
