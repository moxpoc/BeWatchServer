package com.bewatches.server.Controller;

import com.bewatches.server.Model.App.Watch;
import com.bewatches.server.Service.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/watch")
public class WatchController {

    @Autowired
    WatchService watchService;

    @RequestMapping(method = RequestMethod.POST)
    public Watch saveWatch(@RequestBody Watch watch){
        return watchService.save(watch);
    }

    @RequestMapping(value = "{imei}", method = RequestMethod.GET)
    public Watch getWatchByImei(@PathVariable("imei") String imei){
        return watchService.getByImei(imei);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteWatchByImei(@RequestBody String imei){
        watchService.deleteByImei(imei);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateWatch(@RequestBody Watch watch){
        Watch watchOld = watchService.getByImei(watch.getImei());
        watchOld.setDeviceMobileNo(watch.getDeviceMobileNo());
        watchOld.setName(watch.getName());
        watchOld.setOwnerBirthday(watch.getOwnerBirthday());
        watchOld.setOwnerGender(watch.getOwnerGender());
        watchService.save(watchOld);
    }

}
