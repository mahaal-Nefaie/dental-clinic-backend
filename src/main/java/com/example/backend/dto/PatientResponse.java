package com.example.backend.dto;

import lombok.*;
import com.example.backend.entity.Patient;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponse {

    private Integer id;
    private String name;
    private String phone;


public PatientResponse(Patient patient) {
        this.id = patient.getPatientId();
        this.name = patient.getFirstName() + " " + patient.getLastName();
        this.phone = patient.getPhone();
    }
}