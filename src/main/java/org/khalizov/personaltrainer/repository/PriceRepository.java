package org.khalizov.personaltrainer.repository;

import org.khalizov.personaltrainer.model.Price;
import org.khalizov.personaltrainer.model.Sport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Integer> {


    List<Price> findByPricePerHourBetween(BigDecimal min, BigDecimal max);
    List<Price> findByPriceFiveHoursBetween(BigDecimal min, BigDecimal max);
    List<Price> findByPriceTenHoursBetween(BigDecimal min, BigDecimal max);

    List<Price> findAllByOrderByPricePerHourAsc();
    List<Price> findAllByOrderByPricePerHourDesc();
    List<Price> findAllByOrderByPriceFiveHoursAsc();
    List<Price> findAllByOrderByPriceFiveHoursDesc();
    List<Price> findAllByOrderByPriceTenHoursAsc();
    List<Price> findAllByOrderByPriceTenHoursDesc();

    List<Price> findByPricePerHourBetweenAndTrainer_Sport(BigDecimal min, BigDecimal max, Sport sport);
    List<Price> findByPriceFiveHoursBetweenAndTrainer_Sport(BigDecimal min, BigDecimal max, Sport sport);
    List<Price> findByPriceTenHoursBetweenAndTrainer_Sport(BigDecimal min, BigDecimal max, Sport sport);

    List<Price> findAllByTrainer_SportOrderByPricePerHourAsc(Sport sport);
    List<Price> findAllByTrainer_SportOrderByPricePerHourDesc(Sport sport);
    List<Price> findAllByTrainer_SportOrderByPriceFiveHoursAsc(Sport sport);
    List<Price> findAllByTrainer_SportOrderByPriceFiveHoursDesc(Sport sport);
    List<Price> findAllByTrainer_SportOrderByPriceTenHoursAsc(Sport sport);
    List<Price> findAllByTrainer_SportOrderByPriceTenHoursDesc(Sport sport);
}
