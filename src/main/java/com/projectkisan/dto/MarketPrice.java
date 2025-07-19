package com.projectkisan.dto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "market_prices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "crop_name", nullable = false)
    private String cropName;

    @Column(name = "market_name", nullable = false)
    private String marketName;

    @Column(name = "state")
    private String state;

    @Column(name = "district")
    private String district;

    @Column(name = "min_price", precision = 10, scale = 2)
    private BigDecimal minPrice;

    @Column(name = "max_price", precision = 10, scale = 2)
    private BigDecimal maxPrice;

    @Column(name = "modal_price", precision = 10, scale = 2)
    private BigDecimal modalPrice;

    @Column(name = "unit")
    private String unit = "quintal";

    @Column(name = "arrival_date")
    private LocalDateTime arrivalDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public MarketPrice(String cropName, String s, String state, String district, BigDecimal subtract, BigDecimal add, BigDecimal price) {
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}