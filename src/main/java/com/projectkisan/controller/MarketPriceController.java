package com.projectkisan.controller;

import com.projectkisan.model.MarketPriceResponse;
import com.projectkisan.dto.MarketPrice;
import com.projectkisan.service.MarketPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/market")
@CrossOrigin(origins = "*")
public class MarketPriceController {
    
    @Autowired
    private MarketPriceService marketPriceService;
    
    @GetMapping("/price/{cropName}")
    public ResponseEntity<MarketPriceResponse> getCurrentPrice(
            @PathVariable String cropName,
            @RequestParam(defaultValue = "Karnataka") String state,
            @RequestParam(defaultValue = "Bangalore") String district) {
        
        MarketPriceResponse response = marketPriceService.getCurrentPrice(cropName, state, district);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/trend/{cropName}")
    public ResponseEntity<MarketPriceResponse> getMarketTrend(
            @PathVariable String cropName,
            @RequestParam(defaultValue = "Bangalore") String location) {
        
        MarketPriceResponse response = marketPriceService.analyzeMarketTrend(cropName, location);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/history/{cropName}")
    public ResponseEntity<List<MarketPrice>> getPriceHistory(
            @PathVariable String cropName,
            @RequestParam(defaultValue = "Karnataka") String state,
            @RequestParam(defaultValue = "30") int days) {
        
        List<MarketPrice> history = marketPriceService.getPriceHistory(cropName, state, days);
        return ResponseEntity.ok(history);
    }
    
    @GetMapping("/crops")
    public ResponseEntity<List<String>> getAvailableCrops(
            @RequestParam(defaultValue = "Karnataka") String state) {
        
        List<String> crops = marketPriceService.getAvailableCrops(state);
        return ResponseEntity.ok(crops);
    }
    
    @PostMapping("/voice-query")
    public ResponseEntity<MarketPriceResponse> voiceMarketQuery(@RequestBody String audioBase64) {
        // Voice-based market query endpoint
        // Implementation will be added during hackathon with Vertex AI Speech-to-Text
        return ResponseEntity.ok(new MarketPriceResponse());
    }
}