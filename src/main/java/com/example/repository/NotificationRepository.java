package com.example.backend.repository;

import com.example.backend.entity.Notification;
import com.example.backend.entity.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository
        extends JpaRepository<Notification, Integer> {

    List<Notification> findByDoctor_DoctorIdOrderByCreatedAtDesc(
            Integer doctorId
    );

    long countByDoctor_DoctorIdAndIsReadFalse(
            Integer doctorId
    );

    boolean existsByAppointment_AppointmentIdAndType(
            Integer appointmentId,
            NotificationType type
    );
}