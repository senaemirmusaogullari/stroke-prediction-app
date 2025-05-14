package com.example.health_risk.controller;

import com.example.health_risk.model.HealthRisk;
import com.example.health_risk.service.HealthRiskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/health-risk")
@CrossOrigin(origins = "*") // Gerekirse localhost:5500 gibi sınırlandırılabilir
public class HealthRiskController {

    private final HealthRiskService healthRiskService;

    public HealthRiskController(HealthRiskService healthRiskService) {
        this.healthRiskService = healthRiskService;
    }

    // ✅ Tüm sağlık verilerini getir
    @GetMapping
    public ResponseEntity<List<HealthRisk>> getAllHealthRisks() {
        List<HealthRisk> risks = healthRiskService.getAllHealthRisks();
        return ResponseEntity.ok(risks);
    }

    // ✅ ID ile tek bir kayıt getir
    @GetMapping("/{id}")
    public ResponseEntity<HealthRisk> getHealthRiskById(@PathVariable Long id) {
        Optional<HealthRisk> risk = healthRiskService.getHealthRiskById(id);
        return risk.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // ✅ Yeni veri ekle + yapay zeka tahmini yap
    @PostMapping
    public ResponseEntity<HealthRisk> createHealthRisk(@RequestBody HealthRisk healthRisk) {
        HealthRisk created = healthRiskService.addHealthRisk(healthRisk);
        return ResponseEntity.ok(created);
    }

    // ✅ Mevcut veriyi güncelle
    @PutMapping("/{id}")
    public ResponseEntity<HealthRisk> updateHealthRisk(
            @PathVariable Long id,
            @RequestBody HealthRisk updatedRisk
    ) {
        HealthRisk updated = healthRiskService.updateHealthRisk(id, updatedRisk);
        return ResponseEntity.ok(updated);
    }

    // ✅ Veriyi sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHealthRisk(@PathVariable Long id) {
        healthRiskService.deleteHealthRisk(id);
        return ResponseEntity.noContent().build();
    }
}