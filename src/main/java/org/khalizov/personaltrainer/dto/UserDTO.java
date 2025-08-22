package org.khalizov.personaltrainer.dto;

import lombok.Data;
import org.khalizov.personaltrainer.model.Status;
import org.khalizov.personaltrainer.model.UserType;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Integer userid;
    private String nickname;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    private Status status;
    private UserType userType;

}
