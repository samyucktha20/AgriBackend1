package com.projectkisan.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class MarketPriceResponse {
    
    private String cropName;
    private String currentPrice;
    private String priceUnit;
    private String priceRange;
    private String marketTrend;
    private String recommendation;
    private List<MarketData> nearbyMarkets;
    private LocalDateTime lastUpdated;
    
    public static class MarketData {
        private String marketName;
        private String location;
        private BigDecimal price;
        private String distance;
        
        public MarketData(String marketName, String location, BigDecimal price, String distance) {
            this.marketName = marketName;
            this.location = location;
            this.price = price;
            this.distance = distance;
        }
        
        // Getters and Setters
        public String getMarketName() { return marketName; }
        public void setMarketName(String marketName) { this.marketName = marketName; }
        
        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }
        
        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }
        
        public String getDistance() { return distance; }
        public void setDistance(String distance) { this.distance = distance; }
    }
    
    // Constructors
    public MarketPriceResponse() {}
    
    public MarketPriceResponse(String cropName, String currentPrice, String marketTrend, String recommendation) {
        this.cropName = cropName;
        this.currentPrice = currentPrice;
        this.marketTrend = marketTrend;
        this.recommendation = recommendation;
        this.lastUpdated = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getCropName() { return cropName; }
    public void setCropName(String cropName) { this.cropName = cropName; }
    
    public String getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(String currentPrice) { this.currentPrice = currentPrice; }
    
    public String getPriceUnit() { return priceUnit; }
    public void setPriceUnit(String priceUnit) { this.priceUnit = priceUnit; }
    
    public String getPriceRange() { return priceRange; }
    public void setPriceRange(String priceRange) { this.priceRange = priceRange; }
    
    public String getMarketTrend() { return marketTrend; }
    public void setMarketTrend(String marketTrend) { this.marketTrend = marketTrend; }
    
    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }
    
    public List<MarketData> getNearbyMarkets() { return nearbyMarkets; }
    public void setNearbyMarkets(List<MarketData> nearbyMarkets) { this.nearbyMarkets = nearbyMarkets; }
    
    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}