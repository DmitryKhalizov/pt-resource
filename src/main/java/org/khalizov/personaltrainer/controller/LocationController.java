package org.khalizov.personaltrainer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.khalizov.personaltrainer.dto.LocationCreateDTO;
import org.khalizov.personaltrainer.dto.LocationDTO;
import org.khalizov.personaltrainer.exception.ResourceNotFoundException;
import org.khalizov.personaltrainer.mapper.LocationDTOMapper;
import org.khalizov.personaltrainer.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@Tag(name = "Location management", description = "API for managing locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationDTOMapper locationDTOMapper;

    @GetMapping
    @Operation(summary = "See all locations", description = "Fetching a list of all locations")
    public List<LocationDTO> getAll() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get location by id", description = "Fetching location through id")
    public LocationDTO getById(
            @Parameter(description = "Location id", required = true, example = "1")
            @PathVariable Integer id) {
        return locationService.getLocationById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location with id " + id + " not found."));
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Get location by name", description = "Fetching location by name")
    public LocationDTO getLocationByName(
            @Parameter(description = "Location name", required = true, example = "SATS Stureplan")
            @PathVariable String name) {
        return locationService.getLocationByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found"));
    }

    @GetMapping("/city/{city}")
    @Operation(summary = "Get location by city", description = "Fetching locations by city")
    public List<LocationDTO> getLocationsByCity (
            @Parameter(description = "Locations in city", required = true, example = "Stockholm")
            @PathVariable String city) {
        return locationService.getLocationsByCity(city);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new location", description = "Create a new location with provided parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input parameters")
    })
    public LocationDTO createLocation(@Valid @RequestBody LocationCreateDTO dto) {
        return locationService.createLocation(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update location", description = "Update an existing location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location updated successfully"),
            @ApiResponse(responseCode = "404", description = "Location not found"),
            @ApiResponse(responseCode = "409", description = "Location already exists")
    })
    public LocationDTO updateLocation(
            @Parameter(description = "Location ID", required = true, example = "1")
            @PathVariable Integer id,
            @Valid @RequestBody LocationCreateDTO body) {
        return locationService.updateLocation(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete location", description = "Delete an existing location by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Location deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Location not found")
    })
    public void deleteLocation(
            @Parameter(description = "Location ID", required = true, example = "1")
            @PathVariable Integer id) {
        if(!locationService.getLocationById(id).isPresent()) {
            throw new ResourceNotFoundException("Location with id " + id + " not found");
        }
        locationService.deleteLocation(id);
    }
}
