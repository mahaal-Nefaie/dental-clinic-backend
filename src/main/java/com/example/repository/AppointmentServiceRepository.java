package com.example.backend.repository;

import com.example.backend.entity.AppointmentServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AppointmentServiceRepository extends JpaRepository<AppointmentServiceEntity, Integer> {
    List<AppointmentServiceEntity> findByAppointment_AppointmentId(Integer appointmentId);
}