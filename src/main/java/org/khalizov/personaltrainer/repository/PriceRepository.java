package org.khalizov.personaltrainer.repository;

import org.khalizov.personaltrainer.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Integer> {

    List<Price> findByPricePerHour(BigDecimal price);
    List<Price> findByPriceFiveHours(BigDecimal price);
    List<Price> findByPriceTenHours(BigDecimal price);

    List<Price> findByPricePerHourBetween(BigDecimal min, BigDecimal max);
    List<Price> findByPriceFiveHoursBetween(BigDecimal min, BigDecimal max);
    List<Price> findByPriceTenHoursBetween(BigDecimal min, BigDecimal max);

    List<Price> findAllByOrderByPricePerHourAsc();
    List<Price> findAllByOrderByPricePerHourDesc();
    List<Price> findAllByOrderByPriceFiveHoursAsc();
    List<Price> findAllByOrderByPriceFiveHoursDesc();
    List<Price> findAllByOrderByPriceTenHoursAsc();
    List<Price> findAllByOrderByPriceTenHoursDesc();
}
