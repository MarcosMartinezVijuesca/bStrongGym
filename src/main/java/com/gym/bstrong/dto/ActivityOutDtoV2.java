package com.gym.bstrong.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityOutDtoV2 {
    private long id;
    private String name;
    private String description;
    private int capacity;
    private boolean active;
    private int durationMinutes;
    private String monitorName;
    private int totalBookings;
}
