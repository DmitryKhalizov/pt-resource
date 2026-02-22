package org.khalizov.personaltrainer.service;

import org.khalizov.personaltrainer.dto.UserCreateDTO;
import org.khalizov.personaltrainer.dto.UserDTO;
import org.khalizov.personaltrainer.dto.UserUpdateDTO;
import org.khalizov.personaltrainer.mapper.UserDTOMapper;
import org.khalizov.personaltrainer.model.User;
import org.khalizov.personaltrainer.model.UserType;
import org.khalizov.personaltrainer.repository.UserRepository;
import org.khalizov.personaltrainer.model.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDTOMapper userDTOMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDTO> getAllUsers() {
         return userRepository.findAll()
                 .stream()
                 .map(userDTOMapper)
                 .collect(Collectors.toUnmodifiableList());
    }

    public Optional<UserDTO> getUserById(Integer id) {
        return userRepository.findById(id)
                .map(userDTOMapper);
    }

    public Optional<UserDTO> getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
                .map(userDTOMapper);
    }


    public UserDTO createUser(UserCreateDTO dto) {
        userRepository.findByNickname(dto.getNickname()).ifPresent(user -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nickname already exists");
        });

        User user = new User();
        user.setNickname(dto.getNickname());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setCreatedAt(LocalDateTime.now());
        user.setStatus(dto.getStatus() != null ? dto.getStatus() : Status.ACTIVE);
        user.setUserType(dto.getUserType() != null ? dto.getUserType() : UserType.CLIENT);
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));

        try {
            User saved = userRepository.save(user);
            return userDTOMapper.apply(saved);
        } catch (DataIntegrityViolationException e) {
            log.warn("createUser failed due to data integrity: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data");
        }
    }

    public List<UserDTO>getUserByStatus(Status status) {
        return userRepository.findByStatus(status)
                .stream()
                .map(userDTOMapper)
                .toList();
    }

    @Transactional
    public UserDTO updateUser(Integer userId, UserUpdateDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "User with id" + userId + " not found"));

        if (dto.getFirstName() != null) {
            user.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            user.setLastName(dto.getLastName());
        }
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getNickname() != null) {
            String newNickname = dto.getNickname();
            if (!Objects.equals(user.getNickname(), newNickname)) {
                Optional<User> byNickname = userRepository.findByNickname(newNickname);
                if (byNickname.isPresent()) {
                    Integer existingId = byNickname.get().getUserId();
                    if (!Objects.equals(existingId, userId)) {
                        log.warn("Nickname '{}' already in use by userId={}, rejecting update for userId={}",
                                newNickname, existingId, userId);
                        throw new ResponseStatusException(HttpStatus.CONFLICT,
                                "Nickname already exists");
                    }
                }
                user.setNickname(newNickname);
            }
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getPhone() != null) {
            user.setPhone(dto.getPhone());
        }
        if(dto.getStatus() != null) {
            user.setStatus(dto.getStatus());
        }
        if(dto.getUserType() != null) {
            user.setUserType(dto.getUserType());
        }

        try {
            User saved = userRepository.save(user);
            return userDTOMapper.apply(saved);
        } catch(DataIntegrityViolationException ex) {
            log.warn("updateUser failed for userId={} due to data integrity: {}", userId, ex.getMessage(), ex);

            throw new ResponseStatusException(HttpStatus.CONFLICT, "Failed to update user - constraint violation");
        }

    }

    @Transactional
    public void deleteUser(Integer id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User with id " + id + " not found"));

        try {
            userRepository.delete(user);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Cannot delete user due to existing references (e.g., reviews). Remove related data first or use soft delete.");
        }
    }
}