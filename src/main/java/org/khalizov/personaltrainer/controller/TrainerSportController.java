package org.khalizov.personaltrainer.controller;

import org.khalizov.personaltrainer.dto.TrainerLocationDTO;
import org.khalizov.personaltrainer.dto.TrainerSportDTO;
import org.khalizov.personaltrainer.mapper.TrainerSportDTOService;
import org.khalizov.personaltrainer.model.TrainerLocation;
import org.khalizov.personaltrainer.model.TrainerLocationId;
import org.khalizov.personaltrainer.model.TrainerSport;
import org.khalizov.personaltrainer.model.TrainerSportId;
import org.khalizov.personaltrainer.service.TrainerSportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trainer-sport")
public class TrainerSportController {

    @Autowired
    private TrainerSportService trainerSportService;

    @Autowired
    private TrainerSportDTOService trainerSportDTOService;

    @GetMapping
    public List<TrainerSportDTO> getAllTrainerSports() {
        List<TrainerSport> entities = trainerSportService.getAllTrainerSports();
        return trainerSportDTOService.getAllTrainerSportDTOs(entities);
    }

    @GetMapping("/{trainerId}/{sportId}")
    public TrainerSportDTO getTrainerSportById(@PathVariable Integer trainerId, @PathVariable Integer sportId) {
        TrainerSportId id = new TrainerSportId(trainerId, sportId);
        Optional<TrainerSport> entity = trainerSportService.getTrainerSportById(id);
        return entity.map(trainerSportDTOService::toDTO).orElseThrow(null);
    }

    @PostMapping
    public TrainerSportDTO createTrainerSport(@RequestBody TrainerSportDTO dto) {
        TrainerSport entity = trainerSportDTOService.toEntity(dto);
        TrainerSport saved = trainerSportService.saveTrainerSport(entity);
        return trainerSportDTOService.toDTO(saved);
    }

    @DeleteMapping
    public void deleteTrainerSport(@PathVariable Integer trainerId, @PathVariable Integer sportId) {
        TrainerSportId id = new TrainerSportId(trainerId, sportId);
        trainerSportService.deleteTrainerSport(id);
    }
}
