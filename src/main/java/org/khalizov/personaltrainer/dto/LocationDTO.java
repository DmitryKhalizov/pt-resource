package org.khalizov.personaltrainer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
    private Integer locationId;
    private String address;
    private String city;
    private String country;
    private String name;
}
