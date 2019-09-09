/*
 * AP02 Multiple bases locating package (responds:BP02)
 * Сообщение ориентирования по опорным точкам беспроводных сетей
 * Данные в пакете служат основой для определения человеко-понятного адреса
 */
package com.velesov84.serverh10.protocol;

import com.velesov84.serverh10.protocol.utils.BluetoothBase;
import com.velesov84.serverh10.protocol.utils.GsmBase;
import com.velesov84.serverh10.protocol.utils.WifiBase;

import java.util.ArrayList;

/**
 *
 * @author Velesov84
 */

public class PduAP02 extends ProtocolDataUnit {
    
    private String language;
    private int bluetoothCount;
    private int gsmCount;
    private int gsmPos;    
    private int wifiPos;
    
    public PduAP02(String aPacket)
    {
        super(aPacket);
        
        if (params[0].contains("|")) {
            String[] p = params[0].split("|");
            language = p[0];
            bluetoothCount = Integer.valueOf(p[1]);
        } else {
            language = params[0];
            bluetoothCount = 0;
        }             
        
        gsmCount = Integer.valueOf(params[bluetoothCount+2]);
        gsmPos = bluetoothCount+5;        
        wifiPos = gsmPos+gsmCount+1;
    }
    
    public String getDeviceLanguage()
    {
        return language;
    }
    
    public int getBluetoothCount()
    {
        return bluetoothCount;
    }
    
    // Список базовых (опорных) точек блютуз
    
    public ArrayList<BluetoothBase> getBluetoothBases()
    {
        int i,a,b;
        ArrayList<BluetoothBase> result;
        BluetoothBase rec;
        
        result = new ArrayList();
        a = 1; b = a+bluetoothCount;
        
        if (a!=b) {        
            for (i=a; i<b; i++) {            
                rec = new BluetoothBase(params[i]);            
                result.add(rec);
            }
        } else { return null; }
        
        return result;
    }
    
    public boolean addressNeeded()
    {
        return params[bluetoothCount+1].equals("1");
    }
    
    public int getMobileCountryCode()
    {
        return Integer.valueOf(params[bluetoothCount+3]);        
    }
    
    public int getMobileNetworkCode()
    {
        return Integer.valueOf(params[bluetoothCount+4]);
    }        
    
    // Список базовых (опорных) точек GSM
    
    public ArrayList<GsmBase> getGsmBases()
    {
        int i,a,b;
        ArrayList<GsmBase> result;
        GsmBase rec;
        
        result = new ArrayList();
        a = gsmPos; b = a+gsmCount;
        for (i=a; i<b; i++) {            
            rec = new GsmBase(params[i]);            
            result.add(rec);
        }
        
        return result;
    }
    
    // Список опорных точек wifi
    
    public ArrayList<WifiBase> getWifiBases()
    {
        String[] p1;        
        int i;
        ArrayList<WifiBase> result;
        WifiBase rec;
        
        result = new ArrayList();
        p1 = params[wifiPos].split("&");
        for (i=0; i<p1.length; i++) {
            rec = new WifiBase(p1[i]);            
            result.add(rec);
        }
        
        return result;
    }        
    
}
