/*
 * AP85 - ответ часов на пакет BP85
 * 
 * 
 */
package com.velesov84.serverh10.protocol;

import com.velesov84.serverh10.protocol.utils.AlarmEntry;

import java.util.ArrayList;

/**
 *
 * @author Velesov84
 */
public class PduAP85 extends PduAP16 {
    
    public PduAP85(String aPacket)
    {
        super(aPacket);
    }
    
    public boolean getMasterSwitchForAll()
    {
        return params[2].equals("1");        
    }
    
    public int getAlarmsCount()
    {
        return Integer.valueOf(params[3]);        
    }
    
    public ArrayList<AlarmEntry> getAlarms()
    {
        if (getAlarmsCount()>0) {
            
            int i; String[] p;
            AlarmEntry a;

            ArrayList<AlarmEntry> result = new ArrayList();

            for (i=4; i<params.length; i++) {
                params[3] += params[i];
            }
            p = params[3].split("@");
            for (i=0; i<p.length; i++) {
                a = new AlarmEntry(p[i]);
                result.add(a);
            }
            return result;
        
        } else 
            return null;    
    }        
    
}
