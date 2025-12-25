package org.khalizov.personaltrainer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.khalizov.personaltrainer.dto.UserCreateDTO;
import org.khalizov.personaltrainer.dto.UserDTO;
import org.khalizov.personaltrainer.exception.ResourceNotFoundException;
import org.khalizov.personaltrainer.mapper.UserDTOMapper;
import org.khalizov.personaltrainer.model.Status;
import org.khalizov.personaltrainer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "API for managing users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDTOMapper userDTOMapper;

    @GetMapping
    @Operation(summary = "Get all users", description = "Fetching a list of all users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Get user by id", description = "Fetching user through id")
    public UserDTO getUserById(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Integer id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    @GetMapping("/nickname/{nickname}")
    @Operation(summary = "Get user by nickname", description = "Fetching user through nickname")
    public UserDTO getUserByNickname(
            @Parameter(description = "User nickname", required = true, example = "sleepy_joe")
            @PathVariable String nickname) {
        return userService.getUserByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get users by status", description = "Fetching users by status")
    public List<UserDTO> getUserByStatus(
            @Parameter(description = "User status", required = true, example = "ACTIVE")
            @PathVariable Status status) {
        return userService.getUserByStatus(status);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new user", description = "create a new user with provided parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Ivalid input parameters")
    })
    public UserDTO createUser(@Valid @RequestBody UserCreateDTO body) {
        return userService.createUser(body);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user", description = "update an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "409", description = "Nickname already exists")
    })
    public UserDTO updateUser(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Integer userId,
            @Valid @RequestBody UserCreateDTO body) {
        return userService.updateUser(userId, body);
    }

}
