package org.khalizov.personaltrainer.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trainer_locations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerLocation {

    @EmbeddedId
    private TrainerLocationId id;

    @ManyToOne
    @MapsId("trainerId")
    @JoinColumn(name = "trainer_id")
    private Personal_Trainer trainer;

    @ManyToOne
    @MapsId("locationId")
    @JoinColumn(name = "location_id")
    private Location location;
}
