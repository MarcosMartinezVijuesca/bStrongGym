package com.gym.bstrong.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityOutDto {
    private long id;
    private String name;
    private String description;
    private int capacity;
    private int durationMinutes;
    private String monitorName;
}