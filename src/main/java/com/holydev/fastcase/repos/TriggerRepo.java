package com.holydev.fastcase.repos;

import com.holydev.fastcase.entities.service_entities.TriggerStrategy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TriggerRepo extends JpaRepository<TriggerStrategy, Long> {
}
