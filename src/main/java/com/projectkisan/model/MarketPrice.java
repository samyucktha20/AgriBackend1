package com.projectkisan.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "market_prices")
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
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Constructors
    public MarketPrice() {}
    
    public MarketPrice(String cropName, String marketName, String state, String district,
                      BigDecimal minPrice, BigDecimal maxPrice, BigDecimal modalPrice) {
        this.cropName = cropName;
        this.marketName = marketName;
        this.state = state;
        this.district = district;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.modalPrice = modalPrice;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCropName() { return cropName; }
    public void setCropName(String cropName) { this.cropName = cropName; }
    
    public String getMarketName() { return marketName; }
    public void setMarketName(String marketName) { this.marketName = marketName; }
    
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    
    public BigDecimal getMinPrice() { return minPrice; }
    public void setMinPrice(BigDecimal minPrice) { this.minPrice = minPrice; }
    
    public BigDecimal getMaxPrice() { return maxPrice; }
    public void setMaxPrice(BigDecimal maxPrice) { this.maxPrice = maxPrice; }
    
    public BigDecimal getModalPrice() { return modalPrice; }
    public void setModalPrice(BigDecimal modalPrice) { this.modalPrice = modalPrice; }
    
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    
    public LocalDateTime getArrivalDate() { return arrivalDate; }
    public void setArrivalDate(LocalDateTime arrivalDate) { this.arrivalDate = arrivalDate; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}