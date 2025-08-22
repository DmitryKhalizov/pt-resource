package org.khalizov.personaltrainer.mapper;

import org.khalizov.personaltrainer.dto.TrainerLocationDTO;
import org.khalizov.personaltrainer.model.Location;
import org.khalizov.personaltrainer.model.Personal_Trainer;
import org.khalizov.personaltrainer.model.TrainerLocation;
import org.khalizov.personaltrainer.model.TrainerLocationId;
import org.khalizov.personaltrainer.repository.LocationRepository;
import org.khalizov.personaltrainer.repository.PersonalTrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainerLocationDTOService {

    @Autowired
    private PersonalTrainerRepository personalTrainerRepository;

    @Autowired
    private LocationRepository locationRepository;

    public TrainerLocationDTO toDTO (TrainerLocation trainerLocation){
        TrainerLocationDTO dto = new TrainerLocationDTO();
        dto.setTrainerId(trainerLocation.getTrainer().getTrainerId());
        dto.setLocationId(trainerLocation.getLocation().getLocationId());
        return dto;
    }

    public List<TrainerLocationDTO> getAllTrainerLocationDTOs(List<TrainerLocation> trainerLocations){
        return trainerLocations.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public TrainerLocation toEntity(TrainerLocationDTO dto) {
        Personal_Trainer trainer = personalTrainerRepository.findById(dto.getTrainerId())
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));
        Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new IllegalArgumentException("Location not found"));
        TrainerLocationId id = new TrainerLocationId(dto.getTrainerId(), dto.getLocationId());
        TrainerLocation entity = new TrainerLocation();
        entity.setId(id);
        entity.setTrainer(trainer);
        entity.setLocation(location);
        return entity;
    }
}
