package com.example.backend.controller;

import com.example.backend.entity.Doctor;
import com.example.backend.service.DoctorService;
import org.springframework.web.bind.annotation.*;
import com.example.backend.dto.SetPasswordRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.backend.dto.DoctorResponse;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "http://localhost:5173")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/me")
    public DoctorResponse getCurrentDoctor(@AuthenticationPrincipal UserDetails userDetails) {
        Doctor doctor = doctorService.getDoctorByEmail(userDetails.getUsername());
        return new DoctorResponse(doctor);
    }

    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable Integer id) {
        return doctorService.getDoctorById(id);
    }

    @GetMapping("/email/{email}")
    public Doctor getDoctorByEmail(@PathVariable String email) {
        return doctorService.getDoctorByEmail(email);
    }
    @PutMapping("/{id}/password")
public String setPassword(
        @PathVariable Integer id,
        @RequestBody SetPasswordRequest request
) {

    doctorService.setPassword(id, request.getPassword());

    return "Password updated successfully";
}
}