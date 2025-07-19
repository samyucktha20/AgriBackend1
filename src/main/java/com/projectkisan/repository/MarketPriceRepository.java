package com.projectkisan.repository;

import com.projectkisan.model.MarketPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MarketPriceRepository extends JpaRepository<MarketPrice, Long> {
    
    List<MarketPrice> findByCropNameAndStateOrderByCreatedAtDesc(String cropName, String state);
    
    List<MarketPrice> findByCropNameOrderByCreatedAtDesc(String cropName);
    
    @Query("SELECT mp FROM MarketPrice mp WHERE mp.cropName = :cropName AND mp.state = :state AND mp.createdAt >= :fromDate ORDER BY mp.createdAt DESC")
    List<MarketPrice> findRecentPrices(@Param("cropName") String cropName, @Param("state") String state, @Param("fromDate") LocalDateTime fromDate);
    
    @Query("SELECT mp FROM MarketPrice mp WHERE mp.cropName = :cropName AND mp.district = :district ORDER BY mp.createdAt DESC")
    List<MarketPrice> findByLocationNearby(@Param("cropName") String cropName, @Param("district") String district);
    
    @Query("SELECT DISTINCT mp.cropName FROM MarketPrice mp WHERE mp.state = :state")
    List<String> findAvailableCropsByState(@Param("state") String state);
}