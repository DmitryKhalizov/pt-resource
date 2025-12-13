package org.khalizov.personaltrainer.mapper;

import org.khalizov.personaltrainer.dto.UserDTO;
import org.khalizov.personaltrainer.model.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {

    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getNickname(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getCreatedAt(),
                user.getStatus(),
                user.getUserType()
        );
    }
}


