package org.khalizov.personaltrainer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.khalizov.personaltrainer.dto.PersonalTrainerCreateDTO;
import org.khalizov.personaltrainer.dto.PersonalTrainerDTO;
import org.khalizov.personaltrainer.mapper.PersonalTrainerDTOMapper;
import org.khalizov.personaltrainer.model.Status;
import org.khalizov.personaltrainer.service.PersonalTrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
@Tag(name = "Personal Trainer Management", description = "API for managing personal trainers")
public class PersonalTrainerController {

    @Autowired
    private PersonalTrainerService personalTrainerService;

    @Autowired
    private PersonalTrainerDTOMapper personalTrainerDTOMapper;

    @GetMapping
    @Operation(summary = "Get all trainers", description = "Fetching a list of all personal trainers")
    public List<PersonalTrainerDTO> getAllTrainers(){
        return personalTrainerService.getAllTrainers();
    }

    @GetMapping("id/{id}")
    @Operation(summary = "Get personal trainer by id", description = "Fetching personal trainer through id")
    public PersonalTrainerDTO getTrainerById(
            @Parameter(description = "Trainer id", required = true, example = "1")
            @PathVariable Integer id) {
        return  personalTrainerService.getTrainerById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));
    }

    @GetMapping("nickname/{nickname}")
    @Operation(summary = "Get personal trainer by nickname", description = "Fetching personal trainer through nickname")
    public PersonalTrainerDTO getTrainerByNickname(
            @Parameter(description = "Trainer nickname", required = true, example = "knockout")
            @PathVariable String nickname) {
        return  personalTrainerService.getTrainerByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));
    }

    @GetMapping("status/{status}")
    @Operation(summary = "Get personal trainers by status", description = "Fetching personal trainers by status")
    public List <PersonalTrainerDTO> getTrainerByStatus(
            @Parameter(description = "Trainer status", required = true, example = "ACTIVE")
            @PathVariable Status status) {
        return  personalTrainerService.getTrainerByStatus(status);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new personal trainer", description = "create a new personal trainer with provided parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input parameters")
    })
    public PersonalTrainerDTO createTrainer(@Valid @RequestBody PersonalTrainerCreateDTO body) {
        return personalTrainerService.createTrainer(body);
    }

    @PostMapping("/{trainerId}/locations/{locationId}")
    @Operation(summary = "Add another location to trainer", description = "Associate a location with a personal trainer")
    public PersonalTrainerDTO addLocationToTrainer(
            @Parameter(description = "Trainer ID", required = true, example = "1")
            @PathVariable Integer trainerId,
            @Parameter(description = "Location ID", required = true, example = "1")
            @PathVariable Integer locationId) {
        return personalTrainerService.addLocationToTrainer(trainerId, locationId);
    }

    @DeleteMapping("/{trainerId}/locations/{locationId}")
    @Operation(summary = "Remove location from trainer", description = "Remove location association from trainer")
    public PersonalTrainerDTO removeLocationFromTrainer(
            @Parameter(description = "Trainer ID", required = true, example = "1")
            @PathVariable Integer trainerId,
            @Parameter(description = "Location ID", required = true, example = "1")
            @PathVariable Integer locationId) {
        return personalTrainerService.removeLocationFromTrainer(trainerId, locationId);
    }

    @PutMapping("/{trainerId}")
    @Operation(summary = "Update trainer", description = "Update an existing personal trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer updated successfully"),
            @ApiResponse(responseCode = "404", description = "Trainer not found"),
            @ApiResponse(responseCode = "409", description = "Nickname already exists")
    })
    public PersonalTrainerDTO updateTrainer(
            @Parameter(description = "Trainer ID", required = true, example = "1")
            @PathVariable Integer trainerId,
            @Valid @RequestBody PersonalTrainerCreateDTO body) {
        return personalTrainerService.updateTrainer(trainerId, body);
    }

}
