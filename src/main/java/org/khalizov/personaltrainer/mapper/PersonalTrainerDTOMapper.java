package org.khalizov.personaltrainer.mapper;

import org.khalizov.personaltrainer.dto.LocationDTO;
import org.khalizov.personaltrainer.dto.PersonalTrainerDTO;
import org.khalizov.personaltrainer.dto.PriceDTO;
import org.khalizov.personaltrainer.dto.TrainerReviewDTO;
import org.khalizov.personaltrainer.model.PersonalTrainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PersonalTrainerDTOMapper implements Function<PersonalTrainer, PersonalTrainerDTO> {

    @Autowired
    private TrainerReviewDTOMapper trainerReviewDTOMapper;
    @Autowired
    private LocationDTOMapper locationDTOMapper;
    @Autowired
    private PriceDTOMapper priceDTOMapper;

    @Override
    public PersonalTrainerDTO apply(PersonalTrainer trainer) {
        Set<LocationDTO> locations = trainer.getLocations() != null
                ? trainer.getLocations().stream()
                .map(locationDTOMapper)
                .collect(Collectors.toSet())
                : new HashSet<>();

        Set<TrainerReviewDTO> reviews = trainer.getReports() != null
                ? trainer.getReports().stream()
                .map(trainerReviewDTOMapper)
                .collect(Collectors.toSet())
                : new HashSet<>();

        PriceDTO priceDTO = trainer.getPrice() != null
                ? priceDTOMapper.apply(trainer.getPrice())
                : null;

        PersonalTrainerDTO dto = new PersonalTrainerDTO(
                trainer.getTrainerId(),
                trainer.getProfileImage(),
                trainer.getProfileImageType(),
                trainer.getFirstName(),
                trainer.getLastName(),
                trainer.getNickname(),
                trainer.getEmail(),
                trainer.getDescription(),
                trainer.getSport(),
                trainer.getExperienceYears(),
                trainer.getStatus(),
                locations,
                priceDTO,
                reviews
        );

        return dto;
    }
}