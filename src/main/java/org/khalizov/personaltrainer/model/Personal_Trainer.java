package org.khalizov.personaltrainer.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "personal_trainer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Personal_Trainer {

    @Id
    @Column(name = "trainer_id")
    private Integer trainerId;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(name = "certification")
    private String certification;

    @Column(name = "password_hash")
    private String password_hash;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "trainer_users",
        joinColumns = @JoinColumn(name = "trainer_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "trainer_locations",
        joinColumns = @JoinColumn(name = "trainer_id"),
        inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    private Set<Location> locations = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "trainer_reports",
            joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name = "report_id")
    )

    private Set<TrainerReview> reports = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "trainer_sports",
            joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name = "sport_id")
    )
    private Set<Sports> sports = new HashSet<>();

}