package org.khalizov.personaltrainer.repository;

import org.khalizov.personaltrainer.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}
