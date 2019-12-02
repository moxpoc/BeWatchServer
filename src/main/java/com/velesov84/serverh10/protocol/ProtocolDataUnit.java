/*
 * Объект базового пакета протокола H10
 * 
 * Также полностью реализует функционал следующих пакетов
 *    + BP01
 *    + BP03
 *    + BP49
 *    + BPHT
 *    + BPHP
 * 
 */
package com.velesov84.serverh10.protocol;

import java.util.Arrays;

/**
 *
 * @author Velesov84
 */
public class ProtocolDataUnit implements Comparable<ProtocolDataUnit> {
    
    private String commandID;
    protected String IMEI;
    protected String[] params;
    
    public ProtocolDataUnit()
    {
        IMEI = "";
        commandID = "";
    }
    
    public ProtocolDataUnit(String aIMEI, String aCommandID, int aParamsCount)
    {
        IMEI = aIMEI;
        commandID = aCommandID;
        if (aParamsCount>0) {
            params = new String[aParamsCount];
            Arrays.fill(params,"");
        }    
    }
    
    public ProtocolDataUnit(String aPacket)
    {
        IMEI = "";        
        parseParams(aPacket);
    }    
    
    private final void parseParams(String aPacket)
    {
        if (aPacket.length()>0) {
            commandID = ProtocolManager.extractCommandID(aPacket);
            String msg = aPacket.substring(6,aPacket.length()-1);            
            if (msg.charAt(0)==',') { msg = msg.substring(1); }
            params = msg.split(",");
        }
    }
    
    public String toPacket()
    {
        String result;
        int i;
        
        result = "IW"+commandID;
        if (params!=null) {
            if (params.length>1) { result += ","; }
            for (i=0; i<params.length-1; i++) { result += params[i]+","; }
            result += params[i]+"#";
        } else { result += "#"; }        
        return result;
    }   
    
    public void setIMEI(String Value)
    {
        IMEI = Value.substring(0,15);
    }
    
    public String getIMEI()
    {
        return IMEI.substring(0,15);
    }
    
    public String getCommandID()
    {
        return commandID;
    }
    
    public void setCommandID(String Value)
    {
        if (Value.length()==4) { commandID = Value; }
    }
    
    @Override
    public int compareTo(ProtocolDataUnit pdu)
    {
        return IMEI.compareTo(pdu.IMEI);
    }
}
