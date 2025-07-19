package com.projectkisan.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "crop_diagnoses")
public class CropDiagnosis {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farmer_id", nullable = false)
    private Farmer farmer;
    
    @Column(name = "crop_type", nullable = false)
    private String cropType;
    
    @Column(name = "disease_identified")
    private String diseaseIdentified;
    
    @Column(name = "confidence_score")
    private Double confidenceScore;
    
    @Column(name = "symptoms", columnDefinition = "TEXT")
    private String symptoms;
    
    @Column(name = "recommendations", columnDefinition = "TEXT")
    private String recommendations;
    
    @Column(name = "severity_level")
    private String severityLevel;
    
    @Column(name = "image_path")
    private String imagePath;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Constructors
    public CropDiagnosis() {}
    
    public CropDiagnosis(Farmer farmer, String cropType, String diseaseIdentified, 
                        Double confidenceScore, String recommendations) {
        this.farmer = farmer;
        this.cropType = cropType;
        this.diseaseIdentified = diseaseIdentified;
        this.confidenceScore = confidenceScore;
        this.recommendations = recommendations;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Farmer getFarmer() { return farmer; }
    public void setFarmer(Farmer farmer) { this.farmer = farmer; }
    
    public String getCropType() { return cropType; }
    public void setCropType(String cropType) { this.cropType = cropType; }
    
    public String getDiseaseIdentified() { return diseaseIdentified; }
    public void setDiseaseIdentified(String diseaseIdentified) { this.diseaseIdentified = diseaseIdentified; }
    
    public Double getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(Double confidenceScore) { this.confidenceScore = confidenceScore; }
    
    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }
    
    public String getRecommendations() { return recommendations; }
    public void setRecommendations(String recommendations) { this.recommendations = recommendations; }
    
    public String getSeverityLevel() { return severityLevel; }
    public void setSeverityLevel(String severityLevel) { this.severityLevel = severityLevel; }
    
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}