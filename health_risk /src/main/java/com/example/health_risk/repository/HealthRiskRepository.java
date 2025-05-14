package com.example.health_risk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.health_risk.model.HealthRisk;
import java.util.List;

@Repository
public interface HealthRiskRepository extends JpaRepository<HealthRisk, Long> {

    // ✅ Yaşa göre sağlık risklerini getir
    List<HealthRisk> findByAgeGreaterThanEqual(double age);

    // ✅ BMI yüksek olanları getir
    List<HealthRisk> findByBmiGreaterThan(double bmi);

    // ✅ Ortalama glikoz seviyesi yüksek olanları getir
    List<HealthRisk> findByAvgGlucoseLevelGreaterThanEqual(double glucoseLevel);
}