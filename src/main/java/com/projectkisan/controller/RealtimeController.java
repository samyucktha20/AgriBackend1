package com.projectkisan.controller;

import com.projectkisan.service.FirebaseService;
import com.projectkisan.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/realtime")
@CrossOrigin(origins = "*")
public class RealtimeController {
    
    @Autowired
    private FirebaseService firebaseService;
    
    @Autowired
    private NotificationService notificationService;
    
    @GetMapping("/market-price/{cropName}")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> getRealtimePrice(@PathVariable String cropName) {
        return firebaseService.getRealtimeMarketPrice(cropName)
                .thenApply(price -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("cropName", cropName);
                    response.put("currentPrice", price);
                    response.put("source", "realtime");
                    response.put("timestamp", System.currentTimeMillis());
                    return ResponseEntity.ok(response);
                })
                .exceptionally(throwable -> {
                    Map<String, Object> error = new HashMap<>();
                    error.put("error", "Failed to fetch realtime price");
                    error.put("message", throwable.getMessage());
                    return ResponseEntity.badRequest().body(error);
                });
    }
    
    @PostMapping("/subscribe-market/{cropName}")
    public ResponseEntity<Map<String, String>> subscribeToMarketUpdates(@PathVariable String cropName) {
        firebaseService.subscribeToMarketUpdates(cropName, new FirebaseService.MarketUpdateListener() {
            @Override
            public void onPriceUpdate(String crop, Map<String, Object> priceData) {
                System.out.println("Market update received for " + crop + ": " + priceData);
                // In real implementation, this would trigger WebSocket or SSE to frontend
            }
            
            @Override
            public void onError(String error) {
                System.err.println("Market subscription error: " + error);
            }
        });
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "subscribed");
        response.put("cropName", cropName);
        response.put("message", "You will receive real-time updates for " + cropName);
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/send-alert")
    public ResponseEntity<Map<String, String>> sendCustomAlert(@RequestBody Map<String, Object> alertData) {
        Long farmerId = Long.valueOf(alertData.get("farmerId").toString());
        String alertType = alertData.get("type").toString();
        String message = alertData.get("message").toString();
        
        firebaseService.publishFarmerAlert(farmerId, alertType, message);
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "sent");
        response.put("message", "Alert sent successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "healthy");
        health.put("firebase", firebaseService != null ? "connected" : "not configured");
        health.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.ok(health);
    }
}