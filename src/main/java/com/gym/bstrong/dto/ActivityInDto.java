package com.gym.bstrong.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityInDto {
    @NotBlank(message = "Name is mandatory and cannot be empty")
    private String name;
    private String description;
    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity;
    @Min(value = 15, message = "Duration must be at least 15 minutes")
    private int durationMinutes;
    private boolean active;
    @Min(value = 0, message = "Price cannot be negative")
    private float pricePerSession;
    @Min(value = 1, message = "Monitor ID is mandatory")
    private long monitorId;
}
