package org.khalizov.personaltrainer.repository;

import org.khalizov.personaltrainer.model.Sports;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportRepository extends JpaRepository<Sports, Integer> {
}
