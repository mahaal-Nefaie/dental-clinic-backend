package com.example.backend.dto;

import com.example.backend.entity.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private Integer notificationId;

    private String title;

    private String message;

    private Boolean isRead;

    private LocalDateTime createdAt;

    private NotificationType type;

    private Integer appointmentId;
}