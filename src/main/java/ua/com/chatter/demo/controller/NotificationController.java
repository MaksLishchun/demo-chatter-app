package ua.com.chatter.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.com.chatter.demo.firebase.NotificationService;
import ua.com.chatter.demo.model.dto.request.NotificationRequest;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public String sendNotification(@RequestBody NotificationRequest request) {
        notificationService.sendPushNotification(request.getToken(), request.getTitle(), request.getBody());
        return "Notification sent!";
    }
}
