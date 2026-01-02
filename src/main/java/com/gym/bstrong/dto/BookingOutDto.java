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
public class BookingOutDto {
    private long id;
    private LocalDate bookingDate;
    private boolean attended;
    private Integer reviewNote;
    private String reviewText;
    private float pricePaid;


    private String memberName;
    private String activityName;
}