package org.khalizov.personaltrainer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.khalizov.personaltrainer.dto.TrainerReviewCreateDTO;
import org.khalizov.personaltrainer.dto.TrainerReviewDTO;
import org.khalizov.personaltrainer.service.TrainerReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/trainers/{trainerId}/reports")
@Tag(name = "Trainer Reports", description = "API for user reviews on trainer")
public class TrainerReviewController {
    @Autowired
    private TrainerReviewService service;

    @GetMapping
    @Operation(summary = "List reviews for trainer")
    public List<TrainerReviewDTO> list (
            @Parameter(description = "Trainer ID", required = true, example = "1")
            @PathVariable Integer trainerId) {
        return service.listByTrainer(trainerId);
    }

    @PostMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add review for trainer")
    public TrainerReviewDTO create(
            @Parameter(description = "Trainer ID", required = true, example = "1")
            @PathVariable Integer trainerId,
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Integer userId,
            @Valid @RequestBody TrainerReviewCreateDTO body) {
        return service.addReview(trainerId, userId, body);
    }

    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete review by id")
    public void delete(
            @Parameter(description = "Trainer ID", required = true, example = "1")
            @PathVariable Integer trainerId,
            @Parameter(description = "Review ID", required = true, example = "1")
            @PathVariable Integer reviewId) {
        service.deleteReview(reviewId);
    }
}
