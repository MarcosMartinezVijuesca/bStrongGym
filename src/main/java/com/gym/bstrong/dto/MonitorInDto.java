package com.gym.bstrong.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonitorInDto {
    @NotNull(message = "Name is mandatory")
    private String name;
    @NotNull
    @Size(min = 9, max = 9, message = "DNI must have 9 characters")
    private String dni;
    private LocalDate hireDate;
    @Min(value = 0, message = "Salary must be higher than 0")
    private float salary;
    private boolean available;
    private String specialty;
}
