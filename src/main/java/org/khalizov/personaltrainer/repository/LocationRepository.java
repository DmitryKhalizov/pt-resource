package org.khalizov.personaltrainer.repository;

import org.khalizov.personaltrainer.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    Optional<Location> findByName(String name);

    List<Location> findByCity(String city);

}
