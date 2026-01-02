package org.khalizov.personaltrainer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Locations data")
public class LocationDTO {
    private Integer locationId;
    private String address;
    private String city;
    private String country;
    private String name;
}
