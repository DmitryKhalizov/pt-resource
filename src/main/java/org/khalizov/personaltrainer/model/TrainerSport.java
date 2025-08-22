package org.khalizov.personaltrainer.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "trainer_sports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerSport {

    @EmbeddedId
    private TrainerSportId id;

    @ManyToOne
    @MapsId("trainerId")
    @JoinColumn(name = "trainer_id")
    private Personal_Trainer trainer;

    @ManyToOne
    @MapsId("sportId")
    @JoinColumn(name = "sport_id")
    private Sports sport;
}