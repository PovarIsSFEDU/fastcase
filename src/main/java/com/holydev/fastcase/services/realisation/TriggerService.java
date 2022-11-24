package com.holydev.fastcase.services.realisation;


import com.holydev.fastcase.entities.service_entities.TriggerStrategy;
import com.holydev.fastcase.repos.TriggerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TriggerService {
    private final TriggerRepo triggerRepo;

    public TriggerStrategy save(TriggerStrategy strategy) {
        return triggerRepo.saveAndFlush(strategy);
    }
}
