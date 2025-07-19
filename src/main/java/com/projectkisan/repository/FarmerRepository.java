package com.projectkisan.repository;

import com.projectkisan.model.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Long> {
    
    Optional<Farmer> findByEmail(String email);
    
    Optional<Farmer> findByPhoneNumber(String phoneNumber);
    
    List<Farmer> findByLocation(String location);
    
    @Query("SELECT f FROM Farmer f WHERE f.preferredLanguage = :language")
    List<Farmer> findByPreferredLanguage(@Param("language") String language);
    
    @Query("SELECT f FROM Farmer f WHERE f.location LIKE %:location% OR f.name LIKE %:searchTerm%")
    List<Farmer> searchFarmers(@Param("location") String location, @Param("searchTerm") String searchTerm);
}