/*
 * AP01 Locating package, GPS+LBS+Status+Base +WIFI combining package (responds:BP01)
 */
package com.velesov84.serverh10.protocol;

import com.velesov84.serverh10.protocol.utils.*;

import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author Velesov84
 */
public class PduAP01 extends ProtocolDataUnit {
    
    protected int wifiPos;
    
    public PduAP01(String aPacket)
    {
        super(aPacket);
        wifiPos = 5;
    }
    
    public GpsData getGPS()
    {
    // Первый параметр (не полностью)
        String msg = params[0].substring(0,params[0].length()-14);
        try {
            return new GpsData(msg);
        }catch (Exception e){
            return null;
        }
    }
    
    public DeviceStatus getDeviceStatus()
    {
    // 14 последних цифр первого параметра
        String msg = params[0].substring(params[0].length()-14);        
        return new DeviceStatus(msg);
    }
    
    public LbsData getLBS()
    {
    // 2,3,4,5 параметры
        LbsData lbs = new LbsData();
        lbs.mobileCountryCode = Integer.valueOf(params[1]);
        lbs.mobileNetworkCode = Integer.valueOf(params[2]);
        lbs.locationAreaCode = Integer.valueOf(params[3]);
        lbs.cellID = Integer.valueOf(params[4]);        
        return lbs;
    }
    
    public ArrayList<WifiBase> getWifiData()
    {
        String[] wifiNodes;        
        ArrayList<WifiBase> result;
        WifiBase rec;
        int i;
        
        wifiNodes = params[wifiPos].split("&");
        result = new ArrayList();        
        
        for (i=0; i<wifiNodes.length; i++) {
            rec = new WifiBase(wifiNodes[i]);            
            result.add(rec);
        }
        
        return result;
    }
    
    
}
