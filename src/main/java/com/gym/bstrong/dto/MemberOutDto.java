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
public class MemberOutDto {
    private long id;
    private String firstName;
    private String lastName;
    private boolean active;
    private LocalDate registrationDate;
}

