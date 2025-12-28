package com.gym.bstrong.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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

    @Column
    @NotNull
    private String firstName;

    @Column
    @NotNull
    private String lastName;

    @Column
    private LocalDate birthDate;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column
    private boolean active;

    @Column
    private float weight;

    @Column
    @Email
    private String email;

//    @OneToMany(mappedBy = "member")
//    @JsonBackReference(value = "member-subscriptions")
//    private List<Subscription> subscriptions;
//
//    @OneToMany(mappedBy = "member")
//    @JsonBackReference(value = "member-bookings")
//    private List<Booking> bookings;
}
