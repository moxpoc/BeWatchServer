package com.bewatches.server.Service;

import com.bewatches.server.Model.Parent.Location;

public interface LocationService {
    Location save(Location location);
    Location getByImei(String imei);
    Location updateLocation(Location location);
}
