/*
 * AP10 Alarm and Return address Package(responds:BP10)
 * 
 * 
 */
package com.velesov84.serverh10.protocol;

/**
 *
 * @author Velesov84
 */
public class PduAP10 extends PduAP01 {
    
    public static int STATE_NO_ALARM = 0;
    public static int STATE_SOS = 1;
    public static int STATE_LOW_BATTERY = 2;
    public static int STATE_WEARING_NOTICE = 4;
    public static int STATE_FALL_DOWN_ALARM = 6;    
    
    public PduAP10(String aPacket)
    {
        super(aPacket);        
        wifiPos = 8;
    }        
    
    public int getAlarmState()
    {
        return Integer.valueOf(params[5]);
    }
    
    public String getDeviceLanguage()
    {
        return params[6];
    }        
    
    public boolean addressNeeded()
    {
        return params[7].charAt(0)=='1';
    }
    
    public boolean addressWithHyperlink()
    {
        return params[7].charAt(1)=='1';
    }    
}
