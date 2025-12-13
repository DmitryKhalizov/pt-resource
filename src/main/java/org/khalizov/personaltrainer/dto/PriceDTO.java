package org.khalizov.personaltrainer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Price for a trainer")
public class PriceDTO {
    private Integer priceId;
    private BigDecimal pricePerHour;
    private BigDecimal priceFiveHours;
    private BigDecimal priceTenHours;
    private String specialPrice;
}
