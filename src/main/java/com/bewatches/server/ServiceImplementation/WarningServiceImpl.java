package com.bewatches.server.ServiceImplementation;

import com.bewatches.server.Model.Parent.Warning;
import com.bewatches.server.Repository.WarningRepository;
import com.bewatches.server.Service.WarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarningServiceImpl implements WarningService {

    @Autowired
    WarningRepository warningRepository;

    @Override
    public Warning save(Warning warning) {
        return warningRepository.saveAndFlush(warning);
    }
}
