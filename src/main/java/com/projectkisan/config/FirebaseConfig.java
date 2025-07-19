package com.projectkisan.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {
    
    @Value("${firebase.database.url:https://project-kisan-default-rtdb.firebaseio.com}")
    private String databaseUrl;
    
    @Value("${firebase.config.path:krishidraft-firebase-adminsdk-fbsvc-c6f81c6000.json}")
    private String serviceAccountPath;
    
    @PostConstruct
    public void initialize() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                // For development, use mock credentials
                // During hackathon, replace with actual service account
                FirebaseOptions options;
                
                try {
                    InputStream serviceAccount = new ClassPathResource(serviceAccountPath).getInputStream();
                    options = FirebaseOptions.builder()
                            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                            .setDatabaseUrl(databaseUrl)
                            .build();
                } catch (Exception e) {
                    // Fallback for development without Firebase credentials
                    System.out.println("Firebase credentials not found, using mock configuration");
                    return;
                }
                
                FirebaseApp.initializeApp(options);
                System.out.println("Firebase initialized successfully");
            }
        } catch (Exception e) {
            System.err.println("Firebase initialization failed: " + e.getMessage());
        }
    }
    
    @Bean
    public FirebaseDatabase firebaseDatabase() {
        try {
            return FirebaseDatabase.getInstance();
        } catch (Exception e) {
            System.err.println("Firebase Database not available: " + e.getMessage());
            return null;
        }
    }
}