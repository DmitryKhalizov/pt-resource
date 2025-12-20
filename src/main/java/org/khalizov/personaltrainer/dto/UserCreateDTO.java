package org.khalizov.personaltrainer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.khalizov.personaltrainer.model.Status;
import org.khalizov.personaltrainer.model.UserType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User creation request")
public class UserCreateDTO {
    @Schema(description = "Unique nickname for the user", example = "sleepy_joe", required = true)
    @NotBlank
    @Size(min=3, max = 50)
    private String nickname;

    @Schema(description = "First name for the user", example = "Joe", required = true)
    @NotBlank
    @Size(min=2, max = 100)
    private String firstName;

    @Schema(description = "Last name for the user", example = "Biden", required = true)
    @NotBlank
    @Size(min=2, max = 100)
    private String lastName;

    @Schema(description = "Password for the user", example = "Password123", required = true)
    @NotBlank
    @Size(min = 3, max = 100)
    private String password;

    @Schema(description = "Email for the user", example = "sleepy_joe@example.se", required = true)
    @Email
    @NotBlank
    private String email;

    @Schema(description = "Phone nr for the user", example = "123456")
    @Size(max = 30)
    private String phone;

    @Schema(description = "Account status for the user", example = "ACTIVE")
    private Status status;

    @Schema(description = "Type of user", example = "CLIENT")
    private UserType userType;



}
