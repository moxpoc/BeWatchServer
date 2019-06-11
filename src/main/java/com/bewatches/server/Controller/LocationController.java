package com.bewatches.server.Controller;

import com.bewatches.server.Model.Parent.Location;
import com.bewatches.server.Service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    LocationService locationService;

    @RequestMapping(method = RequestMethod.POST)
    public Location saveLocation(@RequestBody Location location){
        return locationService.save(location);
    }
}
