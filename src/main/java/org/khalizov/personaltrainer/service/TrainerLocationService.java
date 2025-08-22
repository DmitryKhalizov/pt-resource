package org.khalizov.personaltrainer.service;

import org.khalizov.personaltrainer.dto.TrainerLocationDTO;
import org.khalizov.personaltrainer.mapper.TrainerLocationDTOService;
import org.khalizov.personaltrainer.mapper.TrainerSportDTOService;
import org.khalizov.personaltrainer.model.TrainerLocation;
import org.khalizov.personaltrainer.model.TrainerLocationId;
import org.khalizov.personaltrainer.repository.TrainerLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerLocationService {

    @Autowired
    private TrainerLocationRepository trainerLocationRepository;

    @Autowired
    private TrainerSportDTOService trainerSportDTOService;
    @Autowired
    private TrainerLocationDTOService trainerLocationDTOService;

    public List<TrainerLocation> getAllTrainerLocations() {
        return trainerLocationRepository.findAll();
    }

    public List<TrainerLocationDTO> getAllTrainerLocationDTOs(){
        List<TrainerLocation> entities = trainerLocationRepository.findAll();
        return trainerLocationDTOService.getAllTrainerLocationDTOs(entities);
    }

    public Optional<TrainerLocation> getTrainerLocationById(TrainerLocationId id) {
        return trainerLocationRepository.findById(id);
    }

    public Optional<TrainerLocationDTO> getTrainerLocationDTOById(TrainerLocationId id) {
        return trainerLocationRepository.findById(id)
                .map(trainerLocationDTOService::toDTO);
    }

    public TrainerLocation saveTrainerLocation(TrainerLocation trainerLocation) {
        return trainerLocationRepository.save(trainerLocation);
    }

    public TrainerLocation saveTrainerLocationDTO(TrainerLocationDTO dto) {
        TrainerLocation entity = trainerLocationDTOService.toEntity(dto);
        return trainerLocationRepository.save(entity);
    }

    public void deleteTrainerLocation(TrainerLocationId id) {
        trainerLocationRepository.deleteById(id);
    }


}