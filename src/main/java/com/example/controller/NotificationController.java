package com.example.backend.controller;

import com.example.backend.dto.NotificationResponse;
import com.example.backend.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;


    @GetMapping
    public List<NotificationResponse> getNotifications(
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        return notificationService
                .getDoctorNotifications(email);
    }


    @GetMapping("/unread-count")
    public long getUnreadCount(
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        return notificationService
                .getUnreadNotificationCount(email);
    }

    @PutMapping("/{notificationId}/read")
public void markAsRead(
        @PathVariable Integer notificationId,
        Authentication authentication
) {

    String email =
            authentication.getName();

    notificationService.markAsRead(
            notificationId,
            email
    );
}
}