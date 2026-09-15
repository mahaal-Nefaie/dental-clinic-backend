package com.example.backend.controller;

import com.example.backend.dto.AppointmentRequest;
import com.example.backend.dto.AppointmentResponse;
import com.example.backend.dto.UpdateAppointmentStatusRequest;
import com.example.backend.entity.Appointment;
import com.example.backend.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AppointmentController {

    private final AppointmentService appointmentService;

   /*  @PostMapping
    public ResponseEntity<Appointment> createAppointment(
            @RequestBody AppointmentRequest request
    ) {
        Appointment appointment = appointmentService.createAppointment(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(appointment);
    }*/

        @PostMapping
   public ResponseEntity<AppointmentResponse> createAppointment(
                @RequestBody AppointmentRequest request) {
                AppointmentResponse response =
            appointmentService.createAppointment(request);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
}


    
    @PatchMapping("/{appointmentId}/status")
     public ResponseEntity<AppointmentResponse> updateStatus(
        @PathVariable Integer appointmentId,
        @RequestBody UpdateAppointmentStatusRequest request,
        @AuthenticationPrincipal UserDetails userDetails
) {

    AppointmentResponse response =
            appointmentService.updateStatus(
                    appointmentId,
                    request.getStatus(),
                    userDetails.getUsername()
            );

    return ResponseEntity.ok(response);
}


    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getDoctorAppointments(
            @PathVariable Integer doctorId
    ) {
        List<Appointment> appointments =
                appointmentService.getDoctorAppointments(doctorId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/my-appointments")
    public ResponseEntity<List<AppointmentResponse>> getMyAppointments(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        List<AppointmentResponse> appointments = 
                appointmentService.getAppointmentsForCurrentDoctor(
                        userDetails.getUsername()
                );

        return ResponseEntity.ok(appointments);
    }
}