package org.khalizov.personaltrainer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Location creation request")
public class LocationCreateDTO {
    @Schema(description = "Adress (street, hse nr) of the location", example = "Victory Street, 42", required = true)
    @NotBlank
    private String address;

    @Schema(description = "City of the location", example = "Stockholm", required = true)
    @NotBlank
    private String city;

    @Schema(description = "Location name", example = "SATS Stureplan", required = true)
    @NotBlank
    private String name;





}
