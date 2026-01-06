package com.gym.bstrong.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionOutDto {
    private long id;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private float price;
    private boolean active;
    private boolean autoRenewal;

    private String memberName;
}