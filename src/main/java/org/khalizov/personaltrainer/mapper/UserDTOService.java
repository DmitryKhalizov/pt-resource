package org.khalizov.personaltrainer.mapper;

import org.khalizov.personaltrainer.dto.UserDTO;
import org.khalizov.personaltrainer.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDTOService {
    public List<UserDTO> getAllUserDTOs(List<User> users) {
        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserid(user.getUserid());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setNickname(user.getNickname());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setUserType(user.getUserType());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}
