package com.example.health_risk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.health_risk.repository")
@EntityScan(basePackages = "com.example.health_risk.model")
@ComponentScan(basePackages = "com.example.health_risk")
public class HealthRiskApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthRiskApplication.class, args);
	}
	
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
