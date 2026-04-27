package com.gym.bstrong.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberInDtoV2 {
    @NotBlank(message = "Name is mandatory and cannot be empty")
    private String firstName;
    @NotBlank(message = "Last name is mandatory and cannot be empty")
    private String lastName;
    private LocalDate birthDate;
    private boolean active;
    @Min(value = 0, message = "Weight must be higher than 0")
    private float weight;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Incorrect format for email")
    private String email;
}
