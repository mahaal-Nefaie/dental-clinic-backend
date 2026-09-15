package com.example.backend.repository;

import com.example.backend.entity.Doctor;
import com.example.backend.entity.DoctorService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorServiceRepository extends JpaRepository<DoctorService, Integer> {

    List<DoctorService> findByService_Id(Integer serviceId);

    @Query("""
        SELECT ds.doctor
        FROM DoctorService ds
        WHERE ds.service.id = :serviceId
        """)
    List<Doctor> findDoctorsByServiceId(@Param("serviceId") Integer serviceId);
}