package com.bewatches.server.Controller;

import com.bewatches.server.Model.Parent.BeatHeart;
import com.bewatches.server.Service.BeatHeartService;
import com.velesov84.serverh10.NodeH10;
import com.velesov84.serverh10.protocol.PduBPXL;
import com.velesov84.serverh10.protocol.ProtocolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/beatheart")
public class BeatHeartController {

    @Autowired
    BeatHeartService beatHeartService;

    @RequestMapping(method = RequestMethod.POST)
    public BeatHeart saveBeatHeart(@RequestBody BeatHeart beatHeart){
        return beatHeartService.save(beatHeart);
    }

    @RequestMapping(value = "{imei}", method = RequestMethod.GET)
    public BeatHeart getBeatHeartByImei(@PathVariable("imei") String imei){
        NodeH10 locationNode = new NodeH10();
        PduBPXL pdu = (PduBPXL) ProtocolManager.buildDataUnit(imei, "BPXL");
        locationNode.postPDU(pdu);
        return beatHeartService.getBeatheartByImei(imei);
    }
}
