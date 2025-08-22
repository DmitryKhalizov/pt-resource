package org.khalizov.personaltrainer.model;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerLocationId implements Serializable {
    private Integer trainerId;
    private Integer locationId;
}
