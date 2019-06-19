package com.bewatches.server.Repository;

import com.bewatches.server.Model.App.Blood;
import com.bewatches.server.Model.App.Watch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodPressureRepository extends JpaRepository<Blood, Long> {
    Blood getBloodPressureByWatch(Watch watch);
    Blood getBloodPressureByImei(String imei);
}
