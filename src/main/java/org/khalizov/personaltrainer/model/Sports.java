package org.khalizov.personaltrainer.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sports {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sport_id")
    private Integer sportId;

    @Column(length = 100, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "sports")
    private Set<Personal_Trainer> trainers = new HashSet<>();
}

