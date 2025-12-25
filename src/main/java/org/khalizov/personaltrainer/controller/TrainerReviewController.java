
package org.khalizov.personaltrainer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.khalizov.personaltrainer.config.CustomOAuth2User;
import org.khalizov.personaltrainer.dto.TrainerReviewCreateDTO;
import org.khalizov.personaltrainer.dto.TrainerReviewDTO;
import org.khalizov.personaltrainer.repository.UserRepository;
import org.khalizov.personaltrainer.service.TrainerReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/trainers/{trainerId}/reports")
@Tag(name = "Trainer Reports", description = "API for user reviews on trainer")
public class TrainerReviewController {

    @Autowired
    private TrainerReviewService service;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @Operation(summary = "List reviews for trainer")
    public List<TrainerReviewDTO> list(
            @Parameter(description = "Trainer ID", required = true, example = "1")
            @PathVariable Integer trainerId) {
        return service.listByTrainer(trainerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add review for trainer")
    public TrainerReviewDTO create(
            @Parameter(description = "Trainer ID", required = true, example = "1")
            @PathVariable Integer trainerId,
            @Valid @RequestBody TrainerReviewCreateDTO body,
            @AuthenticationPrincipal Object principal) {

        Integer userId = extractUserId(principal);
        return service.addReview(trainerId, userId, body);
    }

    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete review by id")
    public void delete(
            @Parameter(description = "Review ID", required = true, example = "1")
            @PathVariable Integer reviewId) {
        service.deleteReview(reviewId);
    }

    private Integer extractUserId(Object principal) {
        if (principal instanceof CustomOAuth2User oAuth2User) {
            return oAuth2User.getUserId();
        } else if (principal instanceof UserDetails userDetails) {
            return userRepository.findByEmail(userDetails.getUsername())
                    .map(u -> u.getUserId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
    }
}