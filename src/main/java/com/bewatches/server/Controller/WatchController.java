package com.bewatches.server.Controller;

import com.bewatches.server.Model.App.Watch;
import com.bewatches.server.Security.jwt.JwtTokenProvider;
import com.bewatches.server.Service.ClientService;
import com.bewatches.server.Service.WatchService;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.velesov84.serverh10.NodeH10;
import com.velesov84.serverh10.protocol.PduBP33;
import com.velesov84.serverh10.protocol.ProtocolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;


@RestController
@RequestMapping("/watch")
public class WatchController {


    private static final Logger LOG = LoggerFactory.getLogger(WatchController.class);
    public static final String FCM = "AAAA1K3OsTo:APA91bFBSYJaZrnY4BxA8LVEgoRTdMtlhQFn2FkBrr_Lw4a8hM9OtAoQelb1unC-7nNMoaqPQjQfG-rxfWVoDMv8i3sbIeBjh_sfVf8exy0sVZ10x72AT3vLg-EoBokpZnDn3rOFr-ke";
    public static final String appToken = "fxWtGTvEh3E:APA91bH_QJH1d1nFNQVqT8KYejS_B2ycwPiQpLSPkxStzlpD2FWFE60RNqtGzyFF833jk--_3FlD04-jOZcSKUY9jNnXPY8t6JuuuAWDWK934VfRO1UNDZTK6Zc5pvaFH0EyZdE_1FIE";
    public static final String FCM_URL = "https://fcm.googleapis.com/fcm/send";

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
    public Watch getWatchByImei(@PathVariable("imei") String imei) throws Exception{
        FileInputStream serviceAccount = new FileInputStream("bewatch-246807-firebase-adminsdk-p1oap-f3aa71fa3a.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://bewatch-246807.firebaseio.com")
                .build();
        FirebaseApp.initializeApp(options);

        Message message = Message.builder()
                .putData("score", "850")
                .putData("time", "2:45")
                .setToken(appToken)
                .build();
        String response = FirebaseMessaging.getInstance().send(message);
        System.out.println("Successfully sent message: " + response);

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
    public Watch updateWatch(@RequestBody Watch watch, @RequestHeader(value = "Authorization") String token){

        Watch watchOld = watchService.getByImei(watch.getImei());
        watchOld.setDeviceMobileNo(watch.getDeviceMobileNo());
        watchOld.setName(watch.getName());
        watchOld.setOwnerBirthday(watch.getOwnerBirthday());
        watchOld.setOwnerGender(watch.getOwnerGender());
        watchOld.setHeight(watch.getHeight());
        watchOld.setWeight(watch.getWeight());
        watchOld.setClient(clientService.findByLogin(jwtTokenProvider.getUsername(token.substring(7))));
        watchService.save(watchOld);
        return watchOld;
    }



}
