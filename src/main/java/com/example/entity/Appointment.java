package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Entity
@Table(name = "appointment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointmentid")
    private Integer appointmentId;

    @Column(name = "appointmentdate", nullable = false)
    private LocalDate appointmentDate;

    @Column(name = "appointmenttime", nullable = false)
    private LocalTime appointmentTime;

   @Enumerated(EnumType.STRING)
   @Column(name = "status")
   private AppointmentStatus status;

    @Column(name = "notes")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "patientid", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctorid", nullable = false)
    private Doctor doctor;

   @OneToMany(
    mappedBy = "appointment",
    cascade = CascadeType.ALL,
    orphanRemoval = true
)
private List<AppointmentServiceEntity> services = new ArrayList<>();

}