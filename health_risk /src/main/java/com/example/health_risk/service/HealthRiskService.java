package com.example.health_risk.service;

import com.example.health_risk.model.HealthRisk;
import com.example.health_risk.repository.HealthRiskRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class HealthRiskService {

    private final HealthRiskRepository repository;
    private final RestTemplate restTemplate;

    public HealthRiskService(HealthRiskRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public List<HealthRisk> getAllHealthRisks() {
        return repository.findAll();
    }

    public Optional<HealthRisk> getHealthRiskById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public HealthRisk addHealthRisk(HealthRisk healthRisk) {
        if (healthRisk.getAge() <= 0 || healthRisk.getBmi() <= 0 || healthRisk.getAvgGlucoseLevel() <= 0) {
            throw new IllegalArgumentException("Yaş, BMI ve glikoz seviyesi sıfırdan büyük olmalıdır.");
        }

        Integer prediction = predictStrokeFromPython(healthRisk);

        if (prediction != null) {
            healthRisk.setStroke(prediction);
        } else {
            throw new IllegalStateException("Yapay zeka tahmini alınamadı.");
        }

        return repository.save(healthRisk);
    }

    @Transactional
    public HealthRisk updateHealthRisk(Long id, HealthRisk updatedRisk) {
        return repository.findById(id).map(existingRisk -> {
            // Temel özellikler
            existingRisk.setGender(updatedRisk.getGender());
            existingRisk.setAge(updatedRisk.getAge());
            existingRisk.setHypertension(updatedRisk.getHypertension());
            existingRisk.setHeartDisease(updatedRisk.getHeartDisease());
            existingRisk.setEverMarried(updatedRisk.getEverMarried());
            existingRisk.setResidenceType(updatedRisk.getResidenceType());
            existingRisk.setAvgGlucoseLevel(updatedRisk.getAvgGlucoseLevel());
            existingRisk.setBmi(updatedRisk.getBmi());

            // One-hot encoded work_type alanları
            existingRisk.setWorkTypeGovtJob(updatedRisk.getWorkTypeGovtJob());
            existingRisk.setWorkTypeNeverWorked(updatedRisk.getWorkTypeNeverWorked());
            existingRisk.setWorkTypePrivate(updatedRisk.getWorkTypePrivate());
            existingRisk.setWorkTypeSelfEmployed(updatedRisk.getWorkTypeSelfEmployed());
            existingRisk.setWorkTypeChildren(updatedRisk.getWorkTypeChildren());

            // One-hot encoded smoking_status alanları
            existingRisk.setSmokingStatusFormerlySmoked(updatedRisk.getSmokingStatusFormerlySmoked());
            existingRisk.setSmokingStatusNeverSmoked(updatedRisk.getSmokingStatusNeverSmoked());
            existingRisk.setSmokingStatusSmokes(updatedRisk.getSmokingStatusSmokes());
            existingRisk.setSmokingStatusUnknown(updatedRisk.getSmokingStatusUnknown());

            return repository.save(existingRisk);
        }).orElseThrow(() -> new IllegalArgumentException("Kayıt bulunamadı. ID: " + id));
    }

    @Transactional
    public void deleteHealthRisk(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Silinecek kayıt bulunamadı. ID: " + id);
        }
        repository.deleteById(id);
    }
    private Integer predictStrokeFromPython(HealthRisk healthRisk) {
        try {
            String pythonApiUrl = "http://127.0.0.1:5000/predict";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> request = new LinkedHashMap<>();
            request.put("gender", healthRisk.getGender());
            request.put("age", healthRisk.getAge());
            request.put("hypertension", healthRisk.getHypertension());
            request.put("heartDisease", healthRisk.getHeartDisease());
            request.put("everMarried", healthRisk.getEverMarried());
            request.put("residenceType", healthRisk.getResidenceType());
            request.put("avgGlucoseLevel", healthRisk.getAvgGlucoseLevel());
            request.put("bmi", healthRisk.getBmi());

            // work_type one-hot
            request.put("workTypeGovtJob", healthRisk.getWorkTypeGovtJob());
            request.put("workTypeNeverWorked", healthRisk.getWorkTypeNeverWorked());
            request.put("workTypePrivate", healthRisk.getWorkTypePrivate());
            request.put("workTypeSelfEmployed", healthRisk.getWorkTypeSelfEmployed());
            request.put("workTypeChildren", healthRisk.getWorkTypeChildren());

            // smoking_status one-hot
            request.put("smokingStatusFormerlySmoked", healthRisk.getSmokingStatusFormerlySmoked());
            request.put("smokingStatusNeverSmoked", healthRisk.getSmokingStatusNeverSmoked());
            request.put("smokingStatusSmokes", healthRisk.getSmokingStatusSmokes());
            request.put("smokingStatusUnknown", healthRisk.getSmokingStatusUnknown());
            
            System.out.println("➡️ Flask API'ye gönderilen veri: " + request);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(pythonApiUrl, entity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Object result = response.getBody().get("stroke");
                if (result != null) {
                    return Integer.parseInt(result.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; // Tahmin alınamazsa default değer
    }
  
}