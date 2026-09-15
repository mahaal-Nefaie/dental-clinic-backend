package com.example.backend.dto;

import com.example.backend.entity.Doctor;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponse {

    private Integer id;
    private String name;
    private String specialization;
    private String email;
    private String phone;

    public DoctorResponse(Doctor doctor) {
        this.id = doctor.getDoctorId();
        this.name = doctor.getFirstName() + " " + doctor.getLastName();
        this.specialization = doctor.getSpecialization();
        this.email = doctor.getEmail();
        this.phone = doctor.getPhone();
    }

}