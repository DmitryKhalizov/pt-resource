package org.khalizov.personaltrainer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.khalizov.personaltrainer.model.Location;
import org.khalizov.personaltrainer.model.Sport;
import org.khalizov.personaltrainer.model.Status;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Personal Trainer data")
public class PersonalTrainerDTO {
    private Integer trainerId;
    private String profileImage;
    private String firstName;
    private String lastName;
    private String nickname;
    private String description;
    private Sport sport;
    private Integer experienceYears;
    private Status status;
    private Set<LocationDTO> locations;
    private PriceDTO price;
    private Set<TrainerReviewDTO> reviews;
}
