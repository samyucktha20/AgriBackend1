package com.projectkisan.controller;

import com.projectkisan.dto.Farmer;
import com.projectkisan.repository.FarmerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/farmers")
@CrossOrigin(origins = "*")
public class FarmerController {
    
    @Autowired
    private FarmerRepository farmerRepository;
    
    @PostMapping("/register")
    public ResponseEntity<Farmer> registerFarmer(@Valid @RequestBody Farmer farmer) {
        try {
            Farmer savedFarmer = farmerRepository.save(farmer);
            return ResponseEntity.ok(savedFarmer);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Farmer> getFarmer(@PathVariable Long id) {
        Optional<Farmer> farmer = farmerRepository.findById(id);
        return farmer.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/by-phone/{phoneNumber}")
    public ResponseEntity<Farmer> getFarmerByPhone(@PathVariable String phoneNumber) {
        Optional<Farmer> farmer = farmerRepository.findByPhoneNumber(phoneNumber);
        return farmer.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Farmer> updateFarmer(@PathVariable Long id, @Valid @RequestBody Farmer farmer) {
        if (!farmerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        farmer.setId(id);
        Farmer updatedFarmer = farmerRepository.save(farmer);
        return ResponseEntity.ok(updatedFarmer);
    }
    
    @GetMapping("/location/{location}")
    public ResponseEntity<List<Farmer>> getFarmersByLocation(@PathVariable String location) {
        List<Farmer> farmers = farmerRepository.findByLocation(location);
        return ResponseEntity.ok(farmers);
    }
}