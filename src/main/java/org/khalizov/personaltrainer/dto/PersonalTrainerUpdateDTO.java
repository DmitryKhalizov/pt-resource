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
@Schema(description = "Personal Trainer update request (partial updates allowed)")
public class PersonalTrainerUpdateDTO {
    @Schema(description = "Trainer ID (for identification)", example = "1")
    private Integer trainerId;

    @Schema(description = "Profile image bytes (optional)")
    private byte[] profileImage;

    @Schema(description = "Profile image MIME type (e.g., image/jpeg)")
    private String profileImageType;

    @Schema(description = "First name (optional)", example = "John")
    @Size(min = 3, max = 50)
    private String firstName;

    @Schema(description = "Last name (optional)", example = "Doe")
    @Size(min = 3, max = 50)
    private String lastName;

    @Schema(description = "Password (optional, re-enter to change)", example = "newpassword123")
    @Pattern(regexp = "^$|.{8,100}", message = "Password must be empty or between 8 and 100 characters")
    private String password;

    @Schema(description = "Nickname (optional)", example = "johndoe")
    @Size(min = 3, max = 50)
    private String nickname;

    @Schema(description = "Email (optional)", example = "john.doe@example.com")
    @Email
    private String email;

    @Schema(description = "Description (optional)", example = "Experienced trainer")
    @Size(min = 3, max = 500)
    private String description;

    @Schema(description = "Sport specialization (optional)", example = "Boxing")
    private Sport sport;

    @Schema(description = "Experience in years (optional)", example = "5")
    @Min(0)
    @Max(60)
    private Integer experienceYears;

    @Schema(description = "Status (optional)", example = "ACTIVE")
    private Status status;

    @Schema(description = "Location ID (optional)", example = "1")
    private Integer locationId;

    @Schema(description = "Price per hour (optional)", example = "50.00")
    private BigDecimal pricePerHour;

    @Schema(description = "Price for 5 hours (optional)", example = "200.00")
    private BigDecimal priceFiveHours;

    @Schema(description = "Price for 10 hours (optional)", example = "350.00")
    private BigDecimal priceTenHours;

    @Schema(description = "Special price (optional)", example = "10% off for groups")
    private String specialPrice;
}