package com.example.backend.security;

import com.example.backend.entity.Doctor; 
import com.example.backend.repository.DoctorRepository; 
import org.springframework.security.core.userdetails.User; 
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.core.userdetails.UsernameNotFoundException; 
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final DoctorRepository doctorRepository;

    public CustomUserDetailsService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Doctor not found"));

        return User.builder()
                .username(doctor.getEmail())
                .password(doctor.getPasswordHash())
                .authorities("DOCTOR")
                .build();
    }

}