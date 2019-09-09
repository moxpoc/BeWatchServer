package com.bewatches.server.Controller;

import com.bewatches.server.Model.App.Blood;
import com.bewatches.server.Model.Parent.Health;
import com.bewatches.server.Service.BloodPressureService;
import com.bewatches.server.Service.HealthService;
import com.velesov84.serverh10.NodeH10;
import com.velesov84.serverh10.protocol.PduBPXY;
import com.velesov84.serverh10.protocol.ProtocolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    HealthService healthService;

    @Autowired
    BloodPressureService bloodPressureService;

    String journalNo = "100010";

    @RequestMapping(method = RequestMethod.POST)
    public Health saveHealth(@RequestBody Health health){
        return healthService.save(health);
    }

    @RequestMapping(value = "{imei}", method = RequestMethod.GET)
    public Blood getBeatHeartByImei(@PathVariable("imei") String imei){
        NodeH10 bloodNode = new NodeH10();
        PduBPXY pdu = (PduBPXY) ProtocolManager.buildDataUnit(imei, "BPXY");
        pdu.setJournalNo(journalNo);
        bloodNode.postPDU(pdu);
        return bloodPressureService.getBloodPressureByImei(imei);
    }
}
