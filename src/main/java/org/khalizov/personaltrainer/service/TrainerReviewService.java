package org.khalizov.personaltrainer.service;

import org.khalizov.personaltrainer.dto.TrainerReviewCreateDTO;
import org.khalizov.personaltrainer.dto.TrainerReviewDTO;
import org.khalizov.personaltrainer.mapper.TrainerReviewDTOMapper;
import org.khalizov.personaltrainer.model.PersonalTrainer;
import org.khalizov.personaltrainer.model.TrainerReview;
import org.khalizov.personaltrainer.model.User;
import org.khalizov.personaltrainer.repository.PersonalTrainerRepository;
import org.khalizov.personaltrainer.repository.TrainerReviewRepository;
import org.khalizov.personaltrainer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainerReviewService {

    @Autowired
    private TrainerReviewRepository trainerReviewRepository;
    @Autowired
    private PersonalTrainerRepository personalTrainerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TrainerReviewDTOMapper mapper;

    @Transactional(readOnly = true)
    public List<TrainerReviewDTO> listByTrainer(Integer trainerId) {
        return trainerReviewRepository.findByTrainer_TrainerId(trainerId)
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    @Transactional
    public TrainerReviewDTO addReview (Integer trainerId, Integer userId, TrainerReviewCreateDTO body) {
        PersonalTrainer trainer = personalTrainerRepository.findById(trainerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainer not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        TrainerReview review = new TrainerReview();
        review.setTrainer(trainer);
        review.setUser(user);
        review.setRating(body.getRating());
        review.setContent(body.getContent());

        TrainerReview saved = trainerReviewRepository.save(review);
        return mapper.apply(saved);
    }

    @Transactional
    public void deleteReview(Integer reviewId) {
        if (!trainerReviewRepository.existsById(reviewId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }
        trainerReviewRepository.deleteById(reviewId);
    }
}
