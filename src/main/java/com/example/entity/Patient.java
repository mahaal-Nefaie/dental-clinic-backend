package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "patient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patientid")
    private Integer patientId;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "firstname", nullable = false)
    private String firstName;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "lastname", nullable = false)
    private String lastName;

    @NotBlank(message = "Phone number is mandatory")
    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    @Column(name = "updatedat")
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}