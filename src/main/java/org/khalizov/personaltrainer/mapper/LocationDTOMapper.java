package org.khalizov.personaltrainer.mapper;

import org.khalizov.personaltrainer.dto.LocationDTO;
import org.khalizov.personaltrainer.model.Location;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class LocationDTOMapper implements Function<Location, LocationDTO> {

    @Override
    public LocationDTO apply(Location location) {
        return new LocationDTO(
                location.getLocationId(),
                location.getAddress(),
                location.getCity(),
                null, // COUNTRY, do not really need it
                location.getName()
        );
    }
}
