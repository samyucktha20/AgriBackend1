package com.projectkisan.dto;

import java.time.LocalDateTime;

public class DiagnosisResponse {
    
    private Long diagnosisId;
    private String diseaseIdentified;
    private Double confidenceScore;
    private String severityLevel;
    private String symptoms;
    private String recommendations;
    private String treatmentSteps;
    private String preventiveMeasures;
    private LocalDateTime diagnosedAt;
    
    // Constructors
    public DiagnosisResponse() {}
    
    public DiagnosisResponse(Long diagnosisId, String diseaseIdentified, Double confidenceScore,
                           String severityLevel, String recommendations) {
        this.diagnosisId = diagnosisId;
        this.diseaseIdentified = diseaseIdentified;
        this.confidenceScore = confidenceScore;
        this.severityLevel = severityLevel;
        this.recommendations = recommendations;
        this.diagnosedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getDiagnosisId() { return diagnosisId; }
    public void setDiagnosisId(Long diagnosisId) { this.diagnosisId = diagnosisId; }
    
    public String getDiseaseIdentified() { return diseaseIdentified; }
    public void setDiseaseIdentified(String diseaseIdentified) { this.diseaseIdentified = diseaseIdentified; }
    
    public Double getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(Double confidenceScore) { this.confidenceScore = confidenceScore; }
    
    public String getSeverityLevel() { return severityLevel; }
    public void setSeverityLevel(String severityLevel) { this.severityLevel = severityLevel; }
    
    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }
    
    public String getRecommendations() { return recommendations; }
    public void setRecommendations(String recommendations) { this.recommendations = recommendations; }
    
    public String getTreatmentSteps() { return treatmentSteps; }
    public void setTreatmentSteps(String treatmentSteps) { this.treatmentSteps = treatmentSteps; }
    
    public String getPreventiveMeasures() { return preventiveMeasures; }
    public void setPreventiveMeasures(String preventiveMeasures) { this.preventiveMeasures = preventiveMeasures; }
    
    public LocalDateTime getDiagnosedAt() { return diagnosedAt; }
    public void setDiagnosedAt(LocalDateTime diagnosedAt) { this.diagnosedAt = diagnosedAt; }
}