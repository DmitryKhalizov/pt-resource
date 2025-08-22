package org.khalizov.personaltrainer.mapper;

import org.khalizov.personaltrainer.dto.PersonalTrainerDTO;
import org.khalizov.personaltrainer.model.Personal_Trainer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonalTrainerDTOService {
    public List<PersonalTrainerDTO> getAllTrainerDTOs(List<Personal_Trainer> trainers) {
        return trainers.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public PersonalTrainerDTO toDTO(Personal_Trainer trainer) {
        PersonalTrainerDTO dto = new PersonalTrainerDTO();
        dto.setTrainerId(trainer.getTrainerId());
        dto.setProfileImage(trainer.getProfileImage());
        dto.setFirstName(trainer.getFirstName());
        dto.setLastName(trainer.getLastName());
        dto.setNickname(trainer.getNickname());
        dto.setDescription(trainer.getDescription());
        dto.setCertification(trainer.getCertification());
        dto.setExperienceYears(trainer.getExperienceYears());
        dto.setStatus(trainer.getStatus());
        return dto;
    }

    public Personal_Trainer toEntity(PersonalTrainerDTO dto) {
        Personal_Trainer trainer = new Personal_Trainer();
        trainer.setTrainerId(dto.getTrainerId());
        trainer.setProfileImage(dto.getProfileImage());
        trainer.setFirstName(dto.getFirstName());
        trainer.setLastName(dto.getLastName());
        trainer.setNickname(dto.getNickname());
        trainer.setDescription(dto.getDescription());
        trainer.setCertification(dto.getCertification());
        trainer.setExperienceYears(dto.getExperienceYears());
        trainer.setStatus(dto.getStatus());
        return trainer;
    }
}