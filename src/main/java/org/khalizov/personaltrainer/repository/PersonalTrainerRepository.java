package org.khalizov.personaltrainer.repository;

import org.khalizov.personaltrainer.model.PersonalTrainer;
import org.khalizov.personaltrainer.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PersonalTrainerRepository extends JpaRepository<PersonalTrainer, Integer> {
    @Query("SELECT DISTINCT p FROM PersonalTrainer p " +
            "LEFT JOIN FETCH p.locations " +
            "LEFT JOIN FETCH p.users " +
            "LEFT JOIN FETCH p.reports " +
            "LEFT JOIN FETCH p.sports")
    List<PersonalTrainer> findAllWithCollections();


    Optional<PersonalTrainer> findByNickname(String nickname);

    List<PersonalTrainer> findByStatus (Status status);

    Optional<PersonalTrainer> findByEmail(String email);

    Optional<PersonalTrainer> findByEmailIgnoreCase(String email);
}
