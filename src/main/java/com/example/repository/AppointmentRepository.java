package com.example.backend.repository;

import com.example.backend.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import com.example.backend.entity.AppointmentStatus;



@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
     List<Appointment> findByDoctor_DoctorId(Integer doctorId);

     List<Appointment> findByDoctor_DoctorIdAndStatusNot(
        Integer doctorId,
        AppointmentStatus status
);


     boolean existsByDoctor_DoctorIdAndAppointmentDateAndAppointmentTime(
          Integer doctorId,
          LocalDate appointmentDate, 
          LocalTime appointmentTime
     );

     
     List<Appointment> findByAppointmentDate(
        LocalDate appointmentDate
);

}