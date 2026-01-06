package com.gym.bstrong.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Member")
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column
    private boolean active;

    @Column
    @Min(value = 0)
    private float weight;

    @Column
    @Email
    private String email;

    @OneToMany(mappedBy = "member")
    @JsonBackReference(value = "member-subscriptions")
    private List<Subscription> subscriptions;

    @OneToMany(mappedBy = "member")
    @JsonBackReference(value = "member-bookings")
    private List<Booking> bookings;
}
