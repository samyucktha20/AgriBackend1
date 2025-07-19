package com.projectkisan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}