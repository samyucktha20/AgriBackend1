package com.projectkisan.dto;

import com.google.firebase.database.annotations.NotNull;

public class DiagnosisRequest {
    
    @NotNull
    private Long farmerId;
    private String cropType;
    private String symptoms;
    private String imageBase64;
    private String location;
    
    // Constructors
    public DiagnosisRequest() {}
    
    public DiagnosisRequest(Long farmerId, String cropType, String symptoms, String imageBase64) {
        this.farmerId = farmerId;
        this.cropType = cropType;
        this.symptoms = symptoms;
        this.imageBase64 = imageBase64;
    }
    
    // Getters and Setters
    public Long getFarmerId() { return farmerId; }
    public void setFarmerId(Long farmerId) { this.farmerId = farmerId; }
    
    public String getCropType() { return cropType; }
    public void setCropType(String cropType) { this.cropType = cropType; }
    
    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }
    
    public String getImageBase64() { return imageBase64; }
    public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}