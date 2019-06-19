package com.bewatches.server.ServiceImplementation;

import com.bewatches.server.Model.App.Blood;
import com.bewatches.server.Model.App.Watch;
import com.bewatches.server.Repository.BloodPressureRepository;
import com.bewatches.server.Service.BloodPressureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BloodPressureServiceImpl implements BloodPressureService {

    @Autowired
    BloodPressureRepository bloodPressureRepository;

    @Override
    public Blood getBloodPressureByWatch(Watch watch) {
        return bloodPressureRepository.getBloodPressureByWatch(watch);
    }

    @Override
    public Blood getBloodPressureByImei(String imei) {
        return bloodPressureRepository.getBloodPressureByImei(imei);
    }

    @Override
    public Blood save(Blood blood) {
        return bloodPressureRepository.saveAndFlush(blood);
    }
}
