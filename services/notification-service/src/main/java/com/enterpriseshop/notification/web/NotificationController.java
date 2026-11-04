package com.enterpriseshop.notification.web;

import com.enterpriseshop.notification.domain.Notification;
import com.enterpriseshop.notification.repo.NotificationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationRepository repo;
    public NotificationController(NotificationRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Notification> recent() { return repo.findTop50ByOrderByCreatedAtDesc(); }
}
