package org.khalizov.personaltrainer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.khalizov.personaltrainer.model.Status;
import org.khalizov.personaltrainer.model.Sport;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Personal Trainer creation request")
public class PersonalTrainerCreateDTO {
    @Schema(description = "Link to profile image of the trainer", example = "www.img.se", required = true)
    @NotBlank
    private String profileImage;

    @Schema(description = "Unique first name for the trainer", example = "mohammed", required = true)
    @NotBlank
    @Size(min=3, max = 50)
    private String firstName;

    @Schema(description = "Unique last name for the trainer", example = "ali", required = true)
    @NotBlank
    @Size(min=3, max = 50)
    private String lastName;

    @Schema(description = "Password for the trainer", example = "password123", required = true)
    @NotBlank
    @Size(min = 8, max = 100)
    private String password;

    @Schema(description = "Unique nickname for the trainer", example = "boxer", required = true)
    @NotBlank
    @Size(min=3, max = 50)
    private String nickname;

    @Schema(description = "Email for the trainer", example = "test@test.se")
    @NotBlank
    @Email
    String email;

    @Schema(description = "Unique description for the trainer", example = "Best boxer on earth", required = true)
    @NotBlank
    @Size(min=3, max = 50)
    private String description;

    @Schema(description = "Sport specialisation of the trainer", example = "Boxing")
    private Sport sport;

    @Schema(description = "Experience in years", example = "5", required = true)
    @NotNull
    @Min(0)
    @Max(60)
    private Integer experienceYears;

    @Schema(description = "Status of the trainer", example = "ACTIVE")
    private Status status;

    @Schema(description = "Location ID where trainer works", example = "1")
    private Integer locationId;

    @NotNull
    private BigDecimal pricePerHour;

    @NotNull
    private BigDecimal priceFiveHours;

    @NotNull
    private BigDecimal priceTenHours;

    private String specialPrice;
}
