package com.projectkisan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketPriceResponse {

    private String cropName;
    private String currentPrice;
    private String priceUnit;
    private String priceRange;
    private String marketTrend;
    private String recommendation;
    private List<MarketData> nearbyMarkets;
    private LocalDateTime lastUpdated;

    public MarketPriceResponse(String cropName, String toString, String trend, String recommendation) {
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MarketData {
        private String marketName;
        private String location;
        private BigDecimal price;
        private String distance;
    }
}