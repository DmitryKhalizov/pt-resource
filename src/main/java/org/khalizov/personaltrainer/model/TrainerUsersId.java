package org.khalizov.personaltrainer.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerUsersId implements Serializable {
    private Integer trainerId;
    private Integer userId;
}
