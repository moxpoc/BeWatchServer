/*
 * BP85 Set an alarm/reminder（respond：AP85）
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
public class PduBP85 extends PduBP0X {
    
    public PduBP85(String aIMEI)
    {
        super(aIMEI,"BP85",5);        
    }
    
    public void setMasterSwitchForAll(boolean Value)
    {
        params[2] = Value ? "1":"0";
    }
    
    public void setAlarms(ArrayList<AlarmEntry> Value)
    {
        int i,sz; String sp;
        AlarmEntry e;
        params[4] = "";
        sz = Value.size();
        params[3] = Integer.toString(sz);
        
        for (i=0; i<sz; i++) {
            sp = i<(sz-1) ? "@":"";
            e = Value.get(i);
            params[4] += e.toString()+sp;
        }
    }    
}
