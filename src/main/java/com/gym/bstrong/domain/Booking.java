package com.gym.bstrong.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Booking")
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "booking_date")
    @NotNull
    private LocalDate bookingDate;

    @Column
    private boolean attended;

    @Column(name = "review_note")
    @Min(value = 1)
    @Max(value = 5)
    private Integer reviewNote;

    @Column(name = "review_test")
    private String reviewText;

    @Column(name = "price_paid")
    @Min(value = 0)
    private float pricePaid;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "activity_id")
    private Activity activity;
}
