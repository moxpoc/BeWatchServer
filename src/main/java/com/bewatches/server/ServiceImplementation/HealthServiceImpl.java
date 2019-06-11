package com.bewatches.server.ServiceImplementation;

import com.bewatches.server.Model.Parent.Health;
import com.bewatches.server.Repository.HealthRepository;
import com.bewatches.server.Service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthServiceImpl implements HealthService {

    @Autowired
    HealthRepository healthRepository;

    @Override
    public Health save(Health health) {
        return healthRepository.saveAndFlush(health);
    }
}
