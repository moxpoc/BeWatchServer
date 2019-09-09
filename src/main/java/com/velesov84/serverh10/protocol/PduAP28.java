/*
 * AP28 - ответ устройства на пакет BP28
 * Сообщает о том, что пакет аудио данных получен или не получен
 * 
 */
package com.velesov84.serverh10.protocol;

import com.velesov84.serverh10.protocol.utils.Hex;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author Velesov84
 */
public class PduAP28 extends ProtocolDataUnit {
    
    public PduAP28(String aPacket)
    {
        super(aPacket);
    }
    
    public String getSenderName()
    {
        String result;
        byte[] b = Hex.toBytes(params[0]);
        try { result = new String(b,"UTF-16"); } catch (UnsupportedEncodingException e) {
            result = new String(b);
        }
        return result;
    }
    
    public String getAdditionalData()
    {
        return params[1];
    }
    
    public int getTotalPacketsCount()
    {
        return Integer.valueOf(params[2]);
    }
    
    public int getSequenceNumber()
    {
        return Integer.valueOf(params[3]);
    }    
    
    public boolean getSuccess()
    {
        return params[4].charAt(0)=='1';
    }        
    
    
    
}
