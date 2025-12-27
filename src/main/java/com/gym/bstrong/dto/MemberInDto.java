package com.gym.bstrong.dto;

import jakarta.validation.constraints.Email;
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
    @NotNull(message = "El nombre es obligatorio")
    private String firstName;
    @NotNull(message = "El apellido es obligatorio")
    private String lastName;
    private LocalDate birthDate;
    private boolean active;
    private float weight;
    @Email(message = "Email con formato incorrecto")
    private String email;
}
