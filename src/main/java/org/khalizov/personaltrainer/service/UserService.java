package org.khalizov.personaltrainer.service;

import org.khalizov.personaltrainer.model.User;
import org.khalizov.personaltrainer.model.UserType;
import org.khalizov.personaltrainer.repository.UserRepository;
import org.khalizov.personaltrainer.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {

        return userRepository.save(user);
    }

    public User createUser(String firstName, String lastName, String nickname, String email, String passwordHash,
                           String phone, UserType userType ) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setNickname(nickname);
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        user.setPhone(phone);
        user.setUserType(userType);
        user.setCreatedAt(java.time.LocalDateTime.now());


        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}