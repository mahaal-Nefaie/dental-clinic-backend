package com.example.backend.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AppointmentRequest {
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String notes;
    private Integer patientId;
    private List<Integer> serviceIds;
}