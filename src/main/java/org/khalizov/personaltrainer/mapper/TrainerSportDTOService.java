package org.khalizov.personaltrainer.mapper;

import org.khalizov.personaltrainer.dto.TrainerSportDTO;
import org.khalizov.personaltrainer.model.*;
import org.khalizov.personaltrainer.repository.PersonalTrainerRepository;
import org.khalizov.personaltrainer.repository.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainerSportDTOService {

    @Autowired
    private PersonalTrainerRepository personalTrainerRepository;

    @Autowired
    private SportRepository sportRepository;

    public TrainerSportDTO toDTO (TrainerSport trainerSport){
        TrainerSportDTO dto = new TrainerSportDTO();
        dto.setTrainerId(trainerSport.getTrainer().getTrainerId());
        dto.setSportId(trainerSport.getSport().getSportId());
        return dto;
    }

    public List<TrainerSportDTO> getAllTrainerSportDTOs(List<TrainerSport> trainerSports){
        return trainerSports.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public TrainerSport toEntity(TrainerSportDTO dto) {
        Personal_Trainer trainer = personalTrainerRepository.findById(dto.getTrainerId())
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));
        Sports sport = sportRepository.findById(dto.getSportId())
                .orElseThrow(() -> new IllegalArgumentException("Sport not found"));
        TrainerSportId id = new TrainerSportId(dto.getTrainerId(), dto.getSportId());
        TrainerSport entity = new TrainerSport();
        entity.setId(id);
        entity.setTrainer(trainer);
        entity.setSport(sport);
        return entity;
    }
}
