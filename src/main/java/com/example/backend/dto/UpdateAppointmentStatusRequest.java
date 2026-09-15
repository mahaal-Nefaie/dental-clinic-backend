package com.example.backend.dto;

import com.example.backend.entity.AppointmentStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAppointmentStatusRequest {

    private AppointmentStatus status;
}