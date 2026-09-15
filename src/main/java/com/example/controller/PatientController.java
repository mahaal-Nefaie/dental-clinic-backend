package com.example.backend.controller;

import com.example.backend.entity.Patient;
import com.example.backend.service.PatientService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "http://localhost:5173")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @PostMapping
    public Patient createPatient(
            @Valid @RequestBody Patient patient,
            HttpServletRequest request
    ) {

        String clientIp = request.getRemoteAddr();

        return patientService.savePatient(patient, clientIp);
    }

}