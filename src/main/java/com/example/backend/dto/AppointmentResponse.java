package com.example.backend.dto;

import com.example.backend.entity.AppointmentStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {

    
    private Integer appointmentId;

    private LocalDate date;

    private LocalTime time;

    private AppointmentStatus status;

    private PatientResponse patient;

    private DoctorResponse doctor;

    private List<ServiceResponse> services;

    private String notes;
}