package com.example.backend.service;

import com.example.backend.entity.Patient;
import com.example.backend.repository.PatientRepository;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    private Bucket resolveBucket(String clientIp) {
        return cache.computeIfAbsent(clientIp, k -> {
            Refill refill = Refill.greedy(1, Duration.ofSeconds(30));
            Bandwidth limit = Bandwidth.classic(1, refill);
            return Bucket.builder().addLimit(limit).build();
        });
    }

    public Patient savePatient(Patient patient, String clientIp) {
        Bucket bucket = resolveBucket(clientIp);
        
        if (!bucket.tryConsume(1)) {
            throw new RuntimeException("Too many requests! Please try again later.");
        }

        return patientRepository.save(patient);
    }
}