package com.projectkisan.repository;

import com.projectkisan.dto.CropDiagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CropDiagnosisRepository extends JpaRepository<CropDiagnosis, Long> {
    
    List<CropDiagnosis> findByFarmerIdOrderByCreatedAtDesc(Long farmerId);
    
    List<CropDiagnosis> findByCropType(String cropType);
    
    @Query("SELECT cd FROM CropDiagnosis cd WHERE cd.farmer.id = :farmerId AND cd.createdAt >= :fromDate")
    List<CropDiagnosis> findRecentDiagnoses(@Param("farmerId") Long farmerId, @Param("fromDate") LocalDateTime fromDate);
    
    @Query("SELECT cd FROM CropDiagnosis cd WHERE cd.diseaseIdentified = :disease AND cd.createdAt >= :fromDate")
    List<CropDiagnosis> findByDiseaseAndDateRange(@Param("disease") String disease, @Param("fromDate") LocalDateTime fromDate);
    
    @Query("SELECT cd.diseaseIdentified, COUNT(cd) FROM CropDiagnosis cd WHERE cd.createdAt >= :fromDate GROUP BY cd.diseaseIdentified ORDER BY COUNT(cd) DESC")
    List<Object[]> getMostCommonDiseases(@Param("fromDate") LocalDateTime fromDate);
}