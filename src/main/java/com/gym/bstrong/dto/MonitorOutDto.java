package com.gym.bstrong.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonitorOutDto {
    private long id;
    private String name;
    private String dni;
    private String specialty;
    private boolean available;
    private LocalDate hireDate;
}
