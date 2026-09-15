package com.example.backend.service;

import com.example.backend.dto.AppointmentRequest;
import com.example.backend.dto.AppointmentResponse;
import com.example.backend.entity.*;
import com.example.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.backend.exception.DoctorAlreadyBookedException;
import java.util.List;
import com.example.backend.mapper.AppointmentMapper;


@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentServiceRepository appointmentServiceRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ServiceRepository serviceRepository;
    private final DoctorServiceRepository doctorServiceRepository;
    private final NotificationService notificationService;


        @Transactional
public AppointmentResponse createAppointment(AppointmentRequest request) {

    Patient patient = patientRepository.findById(request.getPatientId())
            .orElseThrow(() -> new RuntimeException("Patient not found"));

    Integer serviceId = request.getServiceIds().get(0);

    Doctor doctor = findAvailableDoctor(
            serviceId,
            request.getAppointmentDate(),
            request.getAppointmentTime()
    );

    Appointment appointment = new Appointment();

    appointment.setAppointmentDate(request.getAppointmentDate());
    appointment.setAppointmentTime(request.getAppointmentTime());
    appointment.setStatus(AppointmentStatus.PENDING);
    appointment.setNotes(request.getNotes());
    appointment.setPatient(patient);
    appointment.setDoctor(doctor);

    Appointment savedAppointment = appointmentRepository.save(appointment);

    notificationService.createNotification(
        doctor,
        savedAppointment,
        "New Appointment",
        "You have a new appointment on "
                + savedAppointment.getAppointmentDate()
                + " at "
                + savedAppointment.getAppointmentTime(),
        NotificationType.APPOINTMENT_CREATED
        );


    for (Integer serviceIdItem : request.getServiceIds()) {

        com.example.backend.entity.Service serviceEntity =
        serviceRepository.findById(serviceIdItem)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Service not found: " + serviceIdItem
                        )
                );

        AppointmentServiceEntity appointmentService =
                new AppointmentServiceEntity();

        appointmentService.setAppointment(savedAppointment);
        appointmentService.setService(serviceEntity);

        appointmentServiceRepository.save(appointmentService);
    }


    List<AppointmentServiceEntity> appointmentServices =
            appointmentServiceRepository
                    .findByAppointment_AppointmentId(
                            savedAppointment.getAppointmentId()
                    );

    // نحول Entity to DTO
    return AppointmentMapper.toResponse(
            savedAppointment,
            appointmentServices
    );
}

    
public List<Doctor> getDoctorsForService(Integer serviceId) {

    return doctorServiceRepository.findDoctorsByServiceId(serviceId);
}

        @Transactional
    public AppointmentResponse updateStatus(
        Integer appointmentId,
        AppointmentStatus newStatus,
        String email
) {

    Doctor doctor = doctorRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Doctor not found"));

    Appointment appointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(() -> new RuntimeException("Appointment not found"));

    if (!appointment.getDoctor().getDoctorId()
            .equals(doctor.getDoctorId())) {

        throw new RuntimeException(
                "You cannot modify this appointment"
        );
    }

    appointment.setStatus(newStatus);

    Appointment savedAppointment =
            appointmentRepository.save(appointment);

    List<AppointmentServiceEntity> appointmentServices =
            appointmentServiceRepository
                    .findByAppointment_AppointmentId(
                            savedAppointment.getAppointmentId()
                    );

    return AppointmentMapper.toResponse(
            savedAppointment,
            appointmentServices
    );
}


    public List<Appointment> getDoctorAppointments(Integer doctorId) {
        doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        return appointmentRepository.findByDoctor_DoctorId(doctorId);
    }

    public List<AppointmentResponse> getAppointmentsForCurrentDoctor(String email) {
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

               /*  List<Appointment> appointments =
        appointmentRepository.findByDoctor_DoctorIdAndStatusNot(
                doctor.getDoctorId(),
                AppointmentStatus.COMPLETED
        );*/
        List<Appointment> appointments =
    appointmentRepository.findByDoctor_DoctorId(
        doctor.getDoctorId()
    );



    return appointments.stream()
            .map(app -> {
                List<AppointmentServiceEntity> services =
                        appointmentServiceRepository
                                .findByAppointment_AppointmentId(
                                        app.getAppointmentId()
                                );

                return AppointmentMapper.toResponse(app, services);
            })
            .toList();
}


    public Doctor findAvailableDoctor(
        Integer serviceId,
        java.time.LocalDate appointmentDate,
        java.time.LocalTime appointmentTime
) {
    List<Doctor> doctors =
            doctorServiceRepository.findDoctorsByServiceId(serviceId);

    if (doctors.isEmpty()) {
        throw new RuntimeException(
                "No doctors available for this service"
        );
    }

    for (Doctor doctor : doctors) {

        boolean isBusy =
                appointmentRepository
                        .existsByDoctor_DoctorIdAndAppointmentDateAndAppointmentTime(
                                doctor.getDoctorId(),
                                appointmentDate,
                                appointmentTime
                        );

        if (!isBusy) {
            return doctor;
        }
    }

    
    throw new DoctorAlreadyBookedException(
            "Doctor is already booked at this time"
    );
}
}