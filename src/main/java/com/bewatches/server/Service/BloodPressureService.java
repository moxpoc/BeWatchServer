package com.bewatches.server.Service;

import com.bewatches.server.Model.App.Blood;
import com.bewatches.server.Model.App.Watch;

public interface BloodPressureService {

    Blood getBloodPressureByWatch(Watch watch);
    Blood getBloodPressureByImei(String imei);
    Blood save(Blood blood);
}
