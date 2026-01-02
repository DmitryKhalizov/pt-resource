package org.khalizov.personaltrainer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Price creation service")
public class PriceCreateDTO {
    @Schema(description = "Price per hour", example = "100", required = true)
    @NotNull
    @DecimalMin("0.0")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal pricePerHour;

    @Schema(description = "Price for five hours", example = "500", required = true)
    @NotNull
    @DecimalMin("0.0")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal priceFiveHours;

    @Schema(description = "Price for ten hours", example = "1000", required = true)
    @NotNull
    @DecimalMin("0.0")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal priceTenHours;

    @Schema(description = "Special price", example = "SEK 100 per hours in January")
    @Size(max = 255)
    private String specialPrice;
}
