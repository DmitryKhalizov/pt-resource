package org.khalizov.personaltrainer.mapper;

import org.khalizov.personaltrainer.dto.LocationDTO;
import org.khalizov.personaltrainer.dto.PersonalTrainerDTO;
import org.khalizov.personaltrainer.dto.PriceDTO;
import org.khalizov.personaltrainer.dto.TrainerReviewDTO;
import org.khalizov.personaltrainer.model.PersonalTrainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PersonalTrainerDTOMapper implements Function<PersonalTrainer, PersonalTrainerDTO> {

    @Autowired
    private TrainerReviewDTOMapper reviewMapper;

    @Override
    public PersonalTrainerDTO apply(PersonalTrainer personalTrainer) {
        Set<LocationDTO> locationDTOs = personalTrainer.getLocations()
                .stream()
                .map(location -> new LocationDTO(
                        location.getLocationId(),
                        location.getAddress(),
                        location.getCity(),
                        null,
                        location.getName()
                ))
                .collect(Collectors.toSet());

        PriceDTO priceDTO = null;
        if(personalTrainer.getPrice() != null) {
            priceDTO = new PriceDTO(
                    personalTrainer.getPrice().getPriceId(),
                    personalTrainer.getPrice().getPricePerHour(),
                    personalTrainer.getPrice().getPriceFiveHours(),
                    personalTrainer.getPrice().getPriceTenHours(),
                    personalTrainer.getPrice().getSpecialPrice()
            );
        }

        Set<TrainerReviewDTO> reviewDTOs = personalTrainer.getReports()
                .stream()
                .map(reviewMapper)
                .collect(Collectors.toSet());

        PersonalTrainerDTO dto = new PersonalTrainerDTO(
                personalTrainer.getTrainerId(),
                personalTrainer.getProfileImage(),
                personalTrainer.getFirstName(),
                personalTrainer.getLastName(),
                personalTrainer.getNickname(),
                personalTrainer.getEmail(),
                personalTrainer.getDescription(),
                personalTrainer.getSport(),
                personalTrainer.getExperienceYears(),
                personalTrainer.getStatus(),
                locationDTOs,
                priceDTO,
                reviewDTOs
        );
        return dto;
    }

}