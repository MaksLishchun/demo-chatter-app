package ua.com.chatter.demo.firebase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        File file = new File(this.getClass().getClassLoader().getResource("firebase_service_account_key.json").getFile());
        FileInputStream refreshToken = new FileInputStream(file);

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                .build();

        return  FirebaseApp.initializeApp(options);
    }
}
