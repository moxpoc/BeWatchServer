package com.bewatches.server.Repository;

import com.bewatches.server.Model.Parent.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Location getByImei(String imei);
}
