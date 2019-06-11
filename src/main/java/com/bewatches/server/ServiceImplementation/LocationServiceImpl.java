package com.bewatches.server.ServiceImplementation;

import com.bewatches.server.Model.Parent.Location;
import com.bewatches.server.Repository.LocationRepository;
import com.bewatches.server.Service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Override
    public Location save(Location location) {
        return locationRepository.saveAndFlush(location);
    }
}
