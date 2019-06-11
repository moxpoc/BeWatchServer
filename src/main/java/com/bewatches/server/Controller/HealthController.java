package com.bewatches.server.Controller;

import com.bewatches.server.Model.Parent.Health;
import com.bewatches.server.Service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    HealthService healthService;

    @RequestMapping(method = RequestMethod.POST)
    public Health saveHealth(@RequestBody Health health){
        return healthService.save(health);
    }
}
