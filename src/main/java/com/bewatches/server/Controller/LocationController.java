package com.bewatches.server.Controller;

import com.bewatches.server.Model.Parent.Location;
import com.bewatches.server.Service.LocationService;
import com.velesov84.serverh10.NodeH10;
import com.velesov84.serverh10.protocol.PduBP16;
import com.velesov84.serverh10.protocol.PduBP33;
import com.velesov84.serverh10.protocol.PduBPXL;
import com.velesov84.serverh10.protocol.ProtocolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    LocationService locationService;

    @RequestMapping(method = RequestMethod.POST)
    public Location saveLocation(@RequestBody Location location){
        return locationService.save(location);
    }

    @RequestMapping(value = "{imei}", method = RequestMethod.GET)
    public Location getLocation(@PathVariable("imei") String imei){
        NodeH10 locationNode = new NodeH10();
        PduBP16 pdu = (PduBP16) ProtocolManager.buildDataUnit(imei, "BP16");
        locationNode.postPDU(pdu);
        return locationService.getByImei(imei);
    }
}
