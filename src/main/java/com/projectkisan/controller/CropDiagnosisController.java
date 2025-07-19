package com.projectkisan.controller;

import com.projectkisan.model.DiagnosisRequest;
import com.projectkisan.model.DiagnosisResponse;
import com.projectkisan.dto.CropDiagnosis;
import com.projectkisan.service.CropDiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/diagnosis")
@CrossOrigin(origins = "*")
public class CropDiagnosisController {
    
    @Autowired
    private CropDiagnosisService diagnosisService;
    
    @PostMapping("/diagnose")
    public ResponseEntity<DiagnosisResponse> diagnoseCrop(@Valid @RequestBody DiagnosisRequest request) {
        try {
            DiagnosisResponse response = diagnosisService.diagnoseCrop(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/history/{farmerId}")
    public ResponseEntity<List<CropDiagnosis>> getDiagnosisHistory(@PathVariable Long farmerId) {
        List<CropDiagnosis> history = diagnosisService.getFarmerDiagnosisHistory(farmerId);
        return ResponseEntity.ok(history);
    }
    
    @GetMapping("/recent/{farmerId}")
    public ResponseEntity<List<CropDiagnosis>> getRecentDiagnoses(
            @PathVariable Long farmerId,
            @RequestParam(defaultValue = "30") int days) {
        List<CropDiagnosis> recent = diagnosisService.getRecentDiagnoses(farmerId, days);
        return ResponseEntity.ok(recent);
    }
    
    @PostMapping("/voice-diagnose")
    public ResponseEntity<DiagnosisResponse> voiceDiagnose(@RequestBody String audioBase64) {
        // Voice-based diagnosis endpoint
        // Implementation will be added during hackathon with Vertex AI Speech-to-Text
        return ResponseEntity.ok(new DiagnosisResponse());
    }
}