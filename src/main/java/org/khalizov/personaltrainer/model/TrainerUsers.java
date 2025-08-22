package org.khalizov.personaltrainer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trainer_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerUsers {

    @EmbeddedId
    private TrainerUsersId id;

    @ManyToOne
    @MapsId("trainerId")
    @JoinColumn(name = "trainer_id")
    private Personal_Trainer trainer;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
}