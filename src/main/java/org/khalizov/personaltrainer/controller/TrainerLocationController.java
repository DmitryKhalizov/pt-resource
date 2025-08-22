package org.khalizov.personaltrainer.controller;

import org.khalizov.personaltrainer.dto.TrainerLocationDTO;
import org.khalizov.personaltrainer.mapper.TrainerLocationDTOService;
import org.khalizov.personaltrainer.model.TrainerLocation;
import org.khalizov.personaltrainer.model.TrainerLocationId;
import org.khalizov.personaltrainer.service.TrainerLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trainer-locations")
public class TrainerLocationController {

    @Autowired
    private TrainerLocationService trainerLocationService;

    @Autowired
    private TrainerLocationDTOService trainerLocationDTOService;

    @GetMapping
    public List<TrainerLocationDTO> getAllTrainerLocations() {
        List<TrainerLocation> entities = trainerLocationService.getAllTrainerLocations();
        return trainerLocationDTOService.getAllTrainerLocationDTOs(entities);
    }

    @GetMapping("/{trainerId}/{locationId}")
    public TrainerLocationDTO getTrainerLocationById(@PathVariable Integer trainerId, @PathVariable Integer locationId) {
        TrainerLocationId id = new TrainerLocationId(trainerId, locationId);
        Optional<TrainerLocation> entity = trainerLocationService.getTrainerLocationById(id);
        return entity.map(trainerLocationDTOService::toDTO).orElse(null);
    }

    @PostMapping
    public TrainerLocationDTO createTrainerLocation(@RequestBody TrainerLocationDTO dto) {
        TrainerLocation entity = trainerLocationDTOService.toEntity(dto);
        TrainerLocation saved = trainerLocationService.saveTrainerLocation(entity);
        return trainerLocationDTOService.toDTO(saved);
    }

    @DeleteMapping("/{trainerId}/{locationId}")
    public void deleteTrainerLocation(@PathVariable Integer trainerId, @PathVariable Integer locationId) {
        TrainerLocationId id = new TrainerLocationId(trainerId, locationId);
        trainerLocationService.deleteTrainerLocation(id);
    }
}