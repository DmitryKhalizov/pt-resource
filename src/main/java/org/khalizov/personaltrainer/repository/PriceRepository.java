package org.khalizov.personaltrainer.repository;

import org.khalizov.personaltrainer.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Integer> {
}
