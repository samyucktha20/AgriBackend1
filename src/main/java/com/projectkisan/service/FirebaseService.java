package com.projectkisan.service;

import com.google.firebase.database.*;
import com.projectkisan.model.CropDiagnosis;
import com.projectkisan.model.MarketPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class FirebaseService {
    
    @Autowired(required = false)
    private FirebaseDatabase firebaseDatabase;
    
    public void publishDiagnosisUpdate(CropDiagnosis diagnosis) {
        if (firebaseDatabase == null) {
            System.out.println("Firebase not configured - would publish diagnosis update: " + diagnosis.getDiseaseIdentified());
            return;
        }
        
        try {
            DatabaseReference ref = firebaseDatabase.getReference("diagnoses");
            Map<String, Object> diagnosisData = new HashMap<>();
            diagnosisData.put("farmerId", diagnosis.getFarmer().getId());
            diagnosisData.put("cropType", diagnosis.getCropType());
            diagnosisData.put("disease", diagnosis.getDiseaseIdentified());
            diagnosisData.put("confidence", diagnosis.getConfidenceScore());
            diagnosisData.put("severity", diagnosis.getSeverityLevel());
            diagnosisData.put("timestamp", System.currentTimeMillis());
            
            ref.push().setValueAsync(diagnosisData);
        } catch (Exception e) {
            System.err.println("Failed to publish diagnosis update: " + e.getMessage());
        }
    }
    
    public void publishMarketPriceUpdate(MarketPrice marketPrice) {
        if (firebaseDatabase == null) {
            System.out.println("Firebase not configured - would publish market price: " + marketPrice.getCropName() + " - ₹" + marketPrice.getModalPrice());
            return;
        }
        
        try {
            DatabaseReference ref = firebaseDatabase.getReference("market-prices");
            Map<String, Object> priceData = new HashMap<>();
            priceData.put("cropName", marketPrice.getCropName());
            priceData.put("marketName", marketPrice.getMarketName());
            priceData.put("state", marketPrice.getState());
            priceData.put("district", marketPrice.getDistrict());
            priceData.put("minPrice", marketPrice.getMinPrice());
            priceData.put("maxPrice", marketPrice.getMaxPrice());
            priceData.put("modalPrice", marketPrice.getModalPrice());
            priceData.put("timestamp", System.currentTimeMillis());
            
            ref.child(marketPrice.getCropName().toLowerCase()).setValueAsync(priceData);
        } catch (Exception e) {
            System.err.println("Failed to publish market price update: " + e.getMessage());
        }
    }
    
    public void publishFarmerAlert(Long farmerId, String alertType, String message) {
        if (firebaseDatabase == null) {
            System.out.println("Firebase not configured - would send alert to farmer " + farmerId + ": " + message);
            return;
        }
        
        try {
            DatabaseReference ref = firebaseDatabase.getReference("alerts").child(farmerId.toString());
            Map<String, Object> alertData = new HashMap<>();
            alertData.put("type", alertType);
            alertData.put("message", message);
            alertData.put("timestamp", System.currentTimeMillis());
            alertData.put("read", false);
            
            ref.push().setValueAsync(alertData);
        } catch (Exception e) {
            System.err.println("Failed to publish farmer alert: " + e.getMessage());
        }
    }
    
    public CompletableFuture<String> getRealtimeMarketPrice(String cropName) {
        CompletableFuture<String> future = new CompletableFuture<>();
        
        if (firebaseDatabase == null) {
            future.complete("Mock price: ₹2500/quintal (Firebase not configured)");
            return future;
        }
        
        try {
            DatabaseReference ref = firebaseDatabase.getReference("market-prices").child(cropName.toLowerCase());
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Map<String, Object> data = (Map<String, Object>) dataSnapshot.getValue();
                        String price = "₹" + data.get("modalPrice") + "/quintal";
                        future.complete(price);
                    } else {
                        future.complete("Price not available");
                    }
                }
                
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    future.completeExceptionally(new RuntimeException(databaseError.getMessage()));
                }
            });
        } catch (Exception e) {
            future.completeExceptionally(e);
        }
        
        return future;
    }
    
    public void subscribeToMarketUpdates(String cropName, MarketUpdateListener listener) {
        if (firebaseDatabase == null) {
            System.out.println("Firebase not configured - would subscribe to market updates for: " + cropName);
            return;
        }
        
        try {
            DatabaseReference ref = firebaseDatabase.getReference("market-prices").child(cropName.toLowerCase());
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Map<String, Object> data = (Map<String, Object>) dataSnapshot.getValue();
                        listener.onPriceUpdate(cropName, data);
                    }
                }
                
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    listener.onError(databaseError.getMessage());
                }
            });
        } catch (Exception e) {
            System.err.println("Failed to subscribe to market updates: " + e.getMessage());
        }
    }
    
    public interface MarketUpdateListener {
        void onPriceUpdate(String cropName, Map<String, Object> priceData);
        void onError(String error);
    }
}