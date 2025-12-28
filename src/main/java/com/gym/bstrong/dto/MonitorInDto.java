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
    @NotNull(message = "El nombre es obligatorio")
    private String name;
    @NotNull
    @Size(min = 9, max = 9, message = "El DNI debe tener 9 caracteres")
    private String dni;
    private LocalDate hireDate;
    @Min(value = 0, message = "El salario no puede ser negativo")
    private float salary;
    private boolean available;
    private String specialty;
}
