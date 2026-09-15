package com.example.backend.mapper;

import com.example.backend.dto.AppointmentResponse;
import com.example.backend.dto.DoctorResponse;
import com.example.backend.dto.PatientResponse;
import com.example.backend.dto.ServiceResponse;
import com.example.backend.entity.Appointment;
import com.example.backend.entity.AppointmentServiceEntity;

import java.util.List;

public class AppointmentMapper {

    private AppointmentMapper() {
        // Prevent creating an instance of this utility class
    }

    public static AppointmentResponse toResponse(
            Appointment appointment,
            List<AppointmentServiceEntity> appointmentServices
    ) {
        AppointmentResponse response = new AppointmentResponse();

        response.setAppointmentId(appointment.getAppointmentId());
        response.setDate(appointment.getAppointmentDate());
        response.setTime(appointment.getAppointmentTime());
        response.setStatus(appointment.getStatus());
        response.setNotes(appointment.getNotes());

        response.setPatient(
                new PatientResponse(appointment.getPatient())
        );

        response.setDoctor(
                new DoctorResponse(appointment.getDoctor())
        );

        response.setServices(
                appointmentServices.stream()
                        .map(item -> new ServiceResponse(item.getService()))
                        .toList()
        );

        return response;
    }
}
