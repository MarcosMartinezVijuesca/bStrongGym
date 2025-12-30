package com.gym.bstrong.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Activity")
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    private String name;

    @Column
    private String description;

    @Column
    @Min(value = 1)
    private int capacity;

    @Column
    @Min(value = 15)
    private int durationMinutes;

    @Column
    private boolean active;

    @Column
    private float pricePerSession;

    @ManyToOne
    @JoinColumn(name = "monitor_id")
    @JsonManagedReference
    private Monitor monitor;

//    @OneToMany(mappedBy = "activity")
//    @JsonBackReference(value = "activity-bookings")
//    private List<Booking> bookings;
}
