package com.gym.bstrong.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberInDto {
    @NotNull(message = "Name is mandatory")
    private String firstName;
    @NotNull(message = "Last name is mandatory")
    private String lastName;
    private LocalDate birthDate;
    private boolean active;
    @Min(value = 0, message = "weight must be higher than 0")
    private float weight;
    @Email(message = "Incorrect format for email")
    private String email;
}
