package com.projectkisan.service;

import com.projectkisan.dto.Farmer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService {
    
    @Autowired
    private FirebaseService firebaseService;
    
    public void sendDiseaseAlert(Farmer farmer, String disease, String severity) {
        String message = String.format(
            "Disease Alert: %s detected in your crop with %s severity. Check your diagnosis for treatment recommendations.",
            disease, severity
        );
        
        firebaseService.publishFarmerAlert(farmer.getId(), "DISEASE_ALERT", message);
        
        // Mock SMS/WhatsApp notification (implement during hackathon)
        System.out.println("SMS Alert sent to " + farmer.getPhoneNumber() + ": " + message);
    }
    
    public void sendMarketPriceAlert(Farmer farmer, String cropName, String currentPrice, String trend) {
        String message = String.format(
            "Market Update: %s price is %s. Trend: %s. Good time to sell!",
            cropName, currentPrice, trend
        );
        
        firebaseService.publishFarmerAlert(farmer.getId(), "MARKET_ALERT", message);
        
        // Mock push notification
        System.out.println("Push notification sent to farmer " + farmer.getId() + ": " + message);
    }
    
    public void sendSchemeNotification(Farmer farmer, String schemeName, String eligibility) {
        String message = String.format(
            "New Scheme Alert: %s is available. You are %s. Check the app for application details.",
            schemeName, eligibility
        );
        
        firebaseService.publishFarmerAlert(farmer.getId(), "SCHEME_ALERT", message);
        
        // Mock WhatsApp notification
        System.out.println("WhatsApp message sent to " + farmer.getPhoneNumber() + ": " + message);
    }
    
    public void sendWeatherAlert(Farmer farmer, String weatherCondition, String recommendation) {
        String message = String.format(
            "Weather Alert: %s expected. Recommendation: %s",
            weatherCondition, recommendation
        );
        
        firebaseService.publishFarmerAlert(farmer.getId(), "WEATHER_ALERT", message);
    }
    
    public void sendVoiceNotification(Farmer farmer, String audioMessage, String language) {
        // During hackathon, integrate with Vertex AI Text-to-Speech
        Map<String, Object> voiceData = new HashMap<>();
        voiceData.put("audioBase64", audioMessage);
        voiceData.put("language", language);
        voiceData.put("farmerId", farmer.getId());
        
        // Mock voice notification
        System.out.println("Voice notification prepared for farmer " + farmer.getId() + " in " + language);
    }
}