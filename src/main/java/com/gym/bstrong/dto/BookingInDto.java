package com.gym.bstrong.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingInDto {

    @NotNull(message = "Booking date is mandatory")
    private LocalDate bookingDate;
    private boolean attended;
    @Min(value = 1, message = "Minimun note must be at least 1")
    @Max(value = 5, message = "Maximun note is 5")
    private Integer reviewNote;
    private String reviewText;
    @Min(value = 0, message = "Price must be a positive number")
    private float pricePaid;

    @NotNull(message = "ID is mandatory")
    @Min(value = 1, message = "Member ID is not valid")
    private Long memberId;

    @NotNull(message = "Activity ID is mandatory")
    @Min(value = 1, message = "Activity ID is not valid")
    private Long activityId;
}
