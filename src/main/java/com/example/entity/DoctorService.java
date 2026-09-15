package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "doctor_service",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"doctorid", "serviceid"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "doctorid", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "serviceid", nullable = false)
    private Service service;
}