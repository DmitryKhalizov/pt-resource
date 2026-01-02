package org.khalizov.personaltrainer.service;

import org.khalizov.personaltrainer.dto.LocationCreateDTO;
import org.khalizov.personaltrainer.dto.LocationDTO;
import org.khalizov.personaltrainer.mapper.LocationDTOMapper;
import org.khalizov.personaltrainer.model.Location;
import org.khalizov.personaltrainer.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private LocationDTOMapper locationDTOMapper;

    public List<LocationDTO> getAllLocations() {
        return locationRepository.findAll()
                .stream()
                .map(locationDTOMapper)
                .collect(Collectors.toUnmodifiableList());
    }

    public Optional<LocationDTO> getLocationById(Integer id) {
        return locationRepository.findById(id)
                .map(locationDTOMapper);
    }

    public Optional<LocationDTO> getLocationByName(String name) {
        return locationRepository.findByName(name)
                .map(locationDTOMapper);
    }

    public List<LocationDTO> getLocationsByCity (String city) {
        return locationRepository.findByCity(city)
                .stream()
                .map(locationDTOMapper)
                .toList();
    }

    @Transactional
    public LocationDTO updateLocation(Integer id, LocationCreateDTO dto) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Location with id " + id + " not found"));

        if (!location.getName().equals(dto.getName())) {
            locationRepository.findByName(dto.getName()).ifPresent(existing -> {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Location name already exists");
            });
        }

        location.setAddress(dto.getAddress());
        location.setCity(dto.getCity());
        location.setName(dto.getName());

        try {
            Location saved = locationRepository.save(location);
            return locationDTOMapper.apply(saved);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data");
        }
    }

    public LocationDTO createLocation(LocationCreateDTO dto) {
        locationRepository.findByName(dto.getName()).ifPresent(location -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Location name already exists");
        });

        Location location = new Location();
        location.setAddress(dto.getAddress());
        location.setCity(dto.getCity());
        location.setName(dto.getName());

        try{
            Location saved = locationRepository.save(location);
            return locationDTOMapper.apply(saved);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data");
        }
    }

    public void deleteLocation(Integer id) {
        Location location = locationRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Location with id " + id + " not found"));
        locationRepository.delete(location);
    }
}