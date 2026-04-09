package com.example.automationservice.ripocitory;


import com.example.automationservice.entiry.AutomationLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AutomationRepository extends MongoRepository<AutomationLog, String> {
}