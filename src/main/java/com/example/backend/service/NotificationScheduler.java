package com.example.backend.service;

import com.example.backend.entity.Appointment;
import com.example.backend.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final AppointmentRepository appointmentRepository;

    private final NotificationService notificationService;


    @Scheduled(fixedRate = 60000)
    public void checkTodayAppointments() {

        LocalDate today =
                LocalDate.now();


        List<Appointment> appointments =
                appointmentRepository
                        .findByAppointmentDate(today);


        for (Appointment appointment :
                appointments) {

            notificationService
                    .createTodayAppointmentNotification(
                            appointment
                    );
        }
    }
}