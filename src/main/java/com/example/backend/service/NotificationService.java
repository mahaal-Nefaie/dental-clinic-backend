package com.example.backend.service;

import com.example.backend.dto.NotificationResponse;
import com.example.backend.entity.Appointment;
import com.example.backend.entity.Doctor;
import com.example.backend.entity.Notification;
import com.example.backend.entity.NotificationType;
import com.example.backend.repository.DoctorRepository;
import com.example.backend.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final DoctorRepository doctorRepository;


   
    public List<NotificationResponse> getDoctorNotifications(
            String email
    ) {

        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found")
                );

        List<Notification> notifications =
                notificationRepository
                        .findByDoctor_DoctorIdOrderByCreatedAtDesc(
                                doctor.getDoctorId()
                        );

        return notifications.stream()
                .map(notification ->
                        new NotificationResponse(
                                notification.getNotificationId(),
                                notification.getTitle(),
                                notification.getMessage(),
                                notification.getIsRead(),
                                notification.getCreatedAt(),
                                notification.getType(),
                                notification.getAppointment() != null
                                        ? notification.getAppointment().getAppointmentId()
                                        : null
                        )
                )
                .toList();
    }


   
    public long getUnreadNotificationCount(
            String email
    ) {

        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found")
                );

        return notificationRepository
                .countByDoctor_DoctorIdAndIsReadFalse(
                        doctor.getDoctorId()
                );
    }


   
    public Notification createNotification(
            Doctor doctor,
            Appointment appointment,
            String title,
            String message,
            NotificationType type
    ) {

        Notification notification =
                new Notification();

        notification.setDoctor(doctor);

        notification.setAppointment(appointment);

        notification.setTitle(title);

        notification.setMessage(message);

        notification.setIsRead(false);

        notification.setCreatedAt(
                LocalDateTime.now()
        );

        notification.setType(type);

        return notificationRepository.save(
                notification
        );
    }


    
    public void createTodayAppointmentNotification(
            Appointment appointment
    ) {

        LocalDate today =
                LocalDate.now();


       
        if (!appointment.getAppointmentDate()
                .equals(today)) {

            return;
        }


        
        boolean exists =
                notificationRepository
                        .existsByAppointment_AppointmentIdAndType(
                                appointment.getAppointmentId(),
                                NotificationType.APPOINTMENT_TODAY
                        );


       
        if (exists) {
            return;
        }


        Doctor doctor =
                appointment.getDoctor();


        String message =
                "You have an appointment today at "
                + appointment.getAppointmentTime();


        createNotification(
                doctor,
                appointment,
                "Appointment Today",
                message,
                NotificationType.APPOINTMENT_TODAY
        );
    }

    public void markAsRead(
        Integer notificationId,
        String email
) {

    Doctor doctor =
            doctorRepository.findByEmail(email)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Doctor not found"
                            )
                    );

    Notification notification =
            notificationRepository.findById(notificationId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Notification not found"
                            )
                    );

    if (!notification.getDoctor()
            .getDoctorId()
            .equals(doctor.getDoctorId())) {

        throw new RuntimeException(
                "Notification does not belong to this doctor"
        );
    }

    notification.setIsRead(true);

    notificationRepository.save(notification);
}
}