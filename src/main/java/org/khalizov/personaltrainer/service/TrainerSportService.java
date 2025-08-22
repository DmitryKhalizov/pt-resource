package org.khalizov.personaltrainer.service;

import org.khalizov.personaltrainer.model.TrainerSport;
import org.khalizov.personaltrainer.model.TrainerSportId;
import org.khalizov.personaltrainer.repository.TrainerSportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerSportService {

    @Autowired
    private TrainerSportRepository trainerSportRepository;

    public List<TrainerSport> getAllTrainerSports() {
        return trainerSportRepository.findAll();
    }

    public Optional<TrainerSport> getTrainerSportById(TrainerSportId id) {
        return trainerSportRepository.findById(id);
    }

    public TrainerSport saveTrainerSport(TrainerSport trainerSport) {
        return trainerSportRepository.save(trainerSport);
    }

    public void deleteTrainerSport(TrainerSportId id) {
        trainerSportRepository.deleteById(id);
    }
}