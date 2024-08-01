package ua.com.chatter.demo.firebase;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

@Service
public class NotificationService {

    public void sendPushNotification(String token, String title, String body) {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .build();

        FirebaseMessaging.getInstance().sendAsync(message).addListener(() -> {
            System.out.println("Notification sent successfully.");
        }, Runnable::run);
    }
}
