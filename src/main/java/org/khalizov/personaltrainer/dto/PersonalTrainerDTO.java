package org.khalizov.personaltrainer.dto;

import lombok.Data;
import org.khalizov.personaltrainer.model.Status;

@Data
public class PersonalTrainerDTO {
    private Integer trainerId;
    private String profileImage;
    private String firstName;
    private String lastName;
    private String nickname;
    private String description;
    private String certification;
    private Integer experienceYears;
    private Status status;
}
