package com.gym.bstrong.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionInDto {

    @NotNull(message = "Subscription type is mandatory")
    @Pattern(regexp = "MONTHLY|QUARTERLY|ANNUAL", message = "Type of suscription must be: MONTHLY, QUARTERLY or ANNUAL")
    private String type;

    @NotNull(message = "Starting date is mandatory")
    private LocalDate startDate;

    @NotNull(message = "Ending date is mandatory")
    private LocalDate endDate;

    @Min(value = 0, message = "Price cannot be negative")
    private float price;

    private boolean active;

    private boolean autoRenewal;

    @NotNull(message = "Member ID is mandatory")
    @Min(value = 1, message = "Member ID not valid")
    private Long memberId;
}