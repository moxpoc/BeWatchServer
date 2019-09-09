/*
 * BP28 Send audio message to device（responds:AP28）
 * Отправляет аудио сообщение на устройство
 * 
 */
package com.velesov84.serverh10.protocol;

import com.velesov84.serverh10.protocol.utils.Hex;

/**
 *
 * @author Velesov84
 */
public class PduBP28 extends ProtocolDataUnit {
    
    public PduBP28(String aIMEI)
    {
        super(aIMEI,"BP28",6);        
    }
    
    public void setSenderName(String Value)
    {
        try {
            params[0] = Hex.fromBytes(Value.getBytes("UTF-16"));
            params[0] = params[0].substring(18);
        } catch (Exception e) {
            System.out.println("Ошибка кодирования строки: "+e.toString());
            params[0] = "";
        }       
    }
    
    public void setAdditionalData(String Value)
    {
        params[1] = Value;
    }
    
    public void setTotalPacketsCount(int Value)
    {
        params[2] = Integer.toString(Value);
    }
    
    public void setSequenceNumber(int Value)
    {
        params[3] = Integer.toString(Value);
    }    
    
    public void setAudioData(byte[] Value, int aLength)
    {
        params[4] = Integer.toString(aLength);
        params[5] = new String(Value,0,aLength);
    }
    
    
}
