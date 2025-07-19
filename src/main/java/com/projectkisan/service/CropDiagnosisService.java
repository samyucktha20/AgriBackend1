package com.projectkisan.service;

import com.projectkisan.model.DiagnosisRequest;
import com.projectkisan.model.DiagnosisResponse;
import com.projectkisan.dto.CropDiagnosis;
import com.projectkisan.dto.Farmer;
import com.projectkisan.repository.CropDiagnosisRepository;
import com.projectkisan.repository.FarmerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class CropDiagnosisService {
    
    @Autowired
    private CropDiagnosisRepository diagnosisRepository;
    
    @Autowired
    private FarmerRepository farmerRepository;
    
    @Autowired
    private AIService aiService;
    
    @Autowired
    private FirebaseService firebaseService;
    
    @Autowired
    private NotificationService notificationService;
    
    public DiagnosisResponse diagnoseCrop(DiagnosisRequest request) {
        Farmer farmer = farmerRepository.findById(request.getFarmerId())
                .orElseThrow(() -> new RuntimeException("Farmer not found"));
        
        // Mock AI diagnosis (replace with actual Vertex AI Gemini during hackathon)
        DiagnosisResponse mockResponse = generateMockDiagnosis(request);
        
        // Save diagnosis to database
        CropDiagnosis diagnosis = new CropDiagnosis(
            farmer,
            request.getCropType(),
            mockResponse.getDiseaseIdentified(),
            mockResponse.getConfidenceScore(),
            mockResponse.getRecommendations()
        );
        
        diagnosis.setSymptoms(request.getSymptoms());
        diagnosis.setSeverityLevel(mockResponse.getSeverityLevel());
        
        CropDiagnosis savedDiagnosis = diagnosisRepository.save(diagnosis);
        mockResponse.setDiagnosisId(savedDiagnosis.getId());
        
        // Publish to Firebase for real-time updates
        firebaseService.publishDiagnosisUpdate(savedDiagnosis);
        
        // Send notification if severity is high
        if ("High".equals(mockResponse.getSeverityLevel())) {
            notificationService.sendDiseaseAlert(farmer, mockResponse.getDiseaseIdentified(), mockResponse.getSeverityLevel());
        }
        
        return mockResponse;
    }
    
    public List<CropDiagnosis> getFarmerDiagnosisHistory(Long farmerId) {
        return diagnosisRepository.findByFarmerIdOrderByCreatedAtDesc(farmerId);
    }
    
    public List<CropDiagnosis> getRecentDiagnoses(Long farmerId, int days) {
        LocalDateTime fromDate = LocalDateTime.now().minusDays(days);
        return diagnosisRepository.findRecentDiagnoses(farmerId, fromDate);
    }
    
    private DiagnosisResponse generateMockDiagnosis(DiagnosisRequest request) {
        // Mock responses for different crops (replace with actual AI during hackathon)
        Random random = new Random();
        
        String[] diseases = {
            "Early Blight", "Late Blight", "Bacterial Wilt", "Fusarium Wilt",
            "Leaf Spot", "Powdery Mildew", "Mosaic Virus", "Root Rot"
        };
        
        String[] severities = {"Low", "Medium", "High"};
        
        String disease = diseases[random.nextInt(diseases.length)];
        String severity = severities[random.nextInt(severities.length)];
        double confidence = 0.7 + (random.nextDouble() * 0.3); // 70-100% confidence
        
        String recommendations = generateRecommendations(disease, request.getCropType());
        String treatmentSteps = generateTreatmentSteps(disease);
        String preventiveMeasures = generatePreventiveMeasures(disease);
        
        DiagnosisResponse response = new DiagnosisResponse(
            null, disease, confidence, severity, recommendations
        );
        
        response.setTreatmentSteps(treatmentSteps);
        response.setPreventiveMeasures(preventiveMeasures);
        response.setSymptoms(request.getSymptoms());
        
        return response;
    }
    
    private String generateRecommendations(String disease, String cropType) {
        return String.format(
            "Your %s crop shows signs of %s. " +
            "Immediate action required: Apply organic fungicide, " +
            "improve ventilation, and remove affected leaves. " +
            "Consult your local agricultural officer for specific pesticides available in your area.",
            cropType, disease
        );
    }
    
    private String generateTreatmentSteps(String disease) {
        return "1. Remove affected plant parts immediately\n" +
               "2. Apply neem oil spray in the evening\n" +
               "3. Improve drainage and reduce watering frequency\n" +
               "4. Apply copper-based fungicide if symptoms persist\n" +
               "5. Monitor plants daily for 7-10 days";
    }
    
    private String generatePreventiveMeasures(String disease) {
        return "1. Maintain proper plant spacing for air circulation\n" +
               "2. Water at soil level, avoid wetting leaves\n" +
               "3. Rotate crops every season\n" +
               "4. Use disease-resistant varieties\n" +
               "5. Regular inspection and early detection";
    }
}