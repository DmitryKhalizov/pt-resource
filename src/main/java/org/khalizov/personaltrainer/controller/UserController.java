package org.khalizov.personaltrainer.controller;

import org.khalizov.personaltrainer.dto.UserDTO;
import org.khalizov.personaltrainer.mapper.UserDTOService;
import org.khalizov.personaltrainer.model.User;
import org.khalizov.personaltrainer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDTOService userDTOService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return userDTOService.getAllUserDTOs(users);
    }
}
