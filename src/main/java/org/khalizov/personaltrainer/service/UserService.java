package org.khalizov.personaltrainer.service;

import org.hibernate.Hibernate;
import org.khalizov.personaltrainer.dto.UserCreateDTO;
import org.khalizov.personaltrainer.dto.UserDTO;
import org.khalizov.personaltrainer.mapper.UserDTOMapper;
import org.khalizov.personaltrainer.model.PersonalTrainer;
import org.khalizov.personaltrainer.model.User;
import org.khalizov.personaltrainer.model.UserType;
import org.khalizov.personaltrainer.repository.UserRepository;
import org.khalizov.personaltrainer.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
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
    public UserDTO updateUser(Integer userId, UserCreateDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "User with id" + userId + " not found"));

        String incomingNickname = dto.getNickname() !=null ? dto.getNickname().trim() : null;

        if (incomingNickname != null && !user.getNickname().equalsIgnoreCase(incomingNickname)) {
            userRepository.findByNickname(incomingNickname)
                    .ifPresent(existingUser -> {
                        if (!existingUser.getUserId().equals(userId)) {
                            throw new ResponseStatusException(HttpStatus.CONFLICT,
                                    "Nickname already exists");
                        }
                    });
            user.setNickname(incomingNickname);
        }
        if (dto.getFirstName() != null) user.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) user.setLastName(dto.getLastName());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getPhone() != null) user.setPhone(dto.getPhone());
        if (dto.getStatus() != null) user.setStatus(dto.getStatus());
        if (dto.getUserType() != null) user.setUserType(dto.getUserType());

        User saved = userRepository.save(user);
        return userDTOMapper.apply(saved);
    }

    @Transactional
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User with id " + id + " not found"));

        userRepository.delete(user);
    }
}