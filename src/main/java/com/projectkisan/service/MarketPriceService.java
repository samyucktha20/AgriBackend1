package com.projectkisan.service;

import com.projectkisan.dto.MarketPriceResponse;
import com.projectkisan.model.MarketPrice;
import com.projectkisan.repository.MarketPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.projectkisan.service.FirebaseService;
import com.projectkisan.service.NotificationService;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class MarketPriceService {
    
    @Autowired
    private MarketPriceRepository marketPriceRepository;
    
    @Autowired
    private WebClient.Builder webClientBuilder;
    
    @Autowired
    private FirebaseService firebaseService;
    
    @Autowired
    private NotificationService notificationService;
    
    public MarketPriceResponse getCurrentPrice(String cropName, String state, String district) {
        // Mock market data (replace with actual API calls during hackathon)
        MarketPriceResponse response = generateMockMarketData(cropName, state, district);
        
        // Save to database for historical tracking
        saveMarketData(cropName, state, district, response);
        
        return response;
    }
    
    public List<MarketPrice> getPriceHistory(String cropName, String state, int days) {
        LocalDateTime fromDate = LocalDateTime.now().minusDays(days);
        return marketPriceRepository.findRecentPrices(cropName, state, fromDate);
    }
    
    public List<String> getAvailableCrops(String state) {
        return marketPriceRepository.findAvailableCropsByState(state);
    }
    
    public MarketPriceResponse analyzeMarketTrend(String cropName, String location) {
        List<MarketPrice> recentPrices = marketPriceRepository.findByCropNameOrderByCreatedAtDesc(cropName);
        
        if (recentPrices.isEmpty()) {
            return getCurrentPrice(cropName, "Karnataka", location);
        }
        
        // Simple trend analysis
        String trend = analyzeTrend(recentPrices);
        String recommendation = generateRecommendation(trend, cropName);
        
        MarketPriceResponse response = new MarketPriceResponse(cropName, 
            recentPrices.get(0).getModalPrice().toString(), trend, recommendation);
        
        return response;
    }
    
    private MarketPriceResponse generateMockMarketData(String cropName, String state, String district) {
        Random random = new Random();
        
        // Mock price ranges for different crops
        int basePrice = getCropBasePrice(cropName);
        int variation = (int) (basePrice * 0.2); // 20% variation
        
        BigDecimal currentPrice = BigDecimal.valueOf(basePrice + random.nextInt(variation));
        BigDecimal minPrice = currentPrice.subtract(BigDecimal.valueOf(random.nextInt(100)));
        BigDecimal maxPrice = currentPrice.add(BigDecimal.valueOf(random.nextInt(200)));
        
        String[] trends = {"Increasing", "Decreasing", "Stable"};
        String trend = trends[random.nextInt(trends.length)];
        
        String recommendation = generateMarketRecommendation(trend, cropName);
        
        MarketPriceResponse response = new MarketPriceResponse(cropName, 
            "₹" + currentPrice + " per quintal", trend, recommendation);
        
        response.setPriceRange("₹" + minPrice + " - ₹" + maxPrice);
        response.setPriceUnit("per quintal");
        
        // Mock nearby markets
        response.setNearbyMarkets(Arrays.asList(
            new MarketPriceResponse.MarketData("Bangalore APMC", "Bangalore", currentPrice, "25 km"),
            new MarketPriceResponse.MarketData("Mysore Market", "Mysore", currentPrice.subtract(BigDecimal.valueOf(50)), "45 km"),
            new MarketPriceResponse.MarketData("Mandya Mandi", "Mandya", currentPrice.add(BigDecimal.valueOf(30)), "30 km")
        ));
        
        return response;
    }
    
    private int getCropBasePrice(String cropName) {
        // Base prices in rupees per quintal
        switch (cropName.toLowerCase()) {
            case "tomato": return 2500;
            case "onion": return 1800;
            case "potato": return 1500;
            case "rice": return 2000;
            case "wheat": return 2200;
            case "maize": return 1800;
            default: return 2000;
        }
    }
    
    private String analyzeTrend(List<MarketPrice> prices) {
        if (prices.size() < 2) return "Stable";
        
        BigDecimal current = prices.get(0).getModalPrice();
        BigDecimal previous = prices.get(1).getModalPrice();
        
        if (current.compareTo(previous) > 0) return "Increasing";
        else if (current.compareTo(previous) < 0) return "Decreasing";
        else return "Stable";
    }
    
    private String generateRecommendation(String trend, String cropName) {
        switch (trend) {
            case "Increasing":
                return "Good time to sell! Prices are rising. Consider selling within 2-3 days.";
            case "Decreasing":
                return "Hold if possible. Prices are falling. Wait for better rates or consider nearby markets.";
            default:
                return "Prices are stable. You can sell now or wait for seasonal demand increase.";
        }
    }
    
    private String generateMarketRecommendation(String trend, String cropName) {
        String baseRecommendation = generateRecommendation(trend, cropName);
        return baseRecommendation + " Check quality requirements and transportation costs to maximize profit.";
    }
    
    private void saveMarketData(String cropName, String state, String district, MarketPriceResponse response) {
        // Extract price value from formatted string
        String priceStr = response.getCurrentPrice().replaceAll("[^0-9.]", "");
        BigDecimal price = new BigDecimal(priceStr);
        
        MarketPrice marketPrice = new MarketPrice(cropName, district + " Market", state, district,
            price.subtract(BigDecimal.valueOf(100)), 
            price.add(BigDecimal.valueOf(200)),
            price);
        
        marketPrice.setArrivalDate(LocalDateTime.now());
        marketPriceRepository.save(marketPrice);
        
        // Publish to Firebase for real-time updates
        firebaseService.publishMarketPriceUpdate(marketPrice);
    }
}