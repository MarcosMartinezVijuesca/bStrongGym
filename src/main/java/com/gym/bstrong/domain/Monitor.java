package com.gym.bstrong.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Monitor")
@Table(name = "monitors")
public class Monitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    @Size(min = 9, max = 9)
    private String dni;

    @Column(name ="hire_date")
    private LocalDate hireDate;

    @Column
    @Min(value = 0)
    private float salary;

    @Column
    private boolean available;

    @Column
    private String specialty;

//    @OneToMany(mappedBy = "monitor")
//    @JsonBackReference(value = "monitor-activities")
//    private List<Activity> activities;
}
