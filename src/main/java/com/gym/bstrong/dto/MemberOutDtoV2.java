package com.gym.bstrong.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberOutDtoV2 {
    private long id;
    private String firstName;
    private String lastName;
    private boolean active;
    private LocalDate registrationDate;
    private String email;
    private LocalDate birthDate;
}
