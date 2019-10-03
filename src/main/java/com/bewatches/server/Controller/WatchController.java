package com.bewatches.server.Controller;

import com.bewatches.server.Model.App.Watch;
import com.bewatches.server.Security.jwt.JwtTokenProvider;
import com.bewatches.server.Service.ClientService;
import com.bewatches.server.Service.WatchService;
import com.velesov84.serverh10.NodeH10;
import com.velesov84.serverh10.protocol.PduBP33;
import com.velesov84.serverh10.protocol.ProtocolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/watch")
public class WatchController {

    @Autowired
    WatchService watchService;

    @Autowired
    ClientService clientService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @RequestMapping(method = RequestMethod.POST)
    public Watch saveWatch(@RequestBody Watch watch){
        return watchService.save(watch);
    }

    @RequestMapping(value = "{imei}", method = RequestMethod.GET)
    public Watch getWatchByImei(@PathVariable("imei") String imei){
        return watchService.getByImei(imei);
    }

    @RequestMapping(value = "client", method = RequestMethod.GET)
    public Watch getWatchByClient(HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        return watchService.getWatchByClient(clientService.findByLogin(jwtTokenProvider.getUsername(token)));
    }

    @RequestMapping(value = "mode/{imei}", method = RequestMethod.GET)
    public void sendMode(@PathVariable("imei") String imei){
        NodeH10 modeNode = new NodeH10();
        PduBP33 pduBP33 = (PduBP33) ProtocolManager.buildDataUnit(imei, "BP33");
        pduBP33.setWorkingMode(PduBP33.EMERGENCY_MODE);
        modeNode.postPDU(pduBP33);
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
        watchOld.setHeight(watch.getHeight());
        watchOld.setWeight(watch.getWeight());
        watchService.save(watchOld);
    }

}
