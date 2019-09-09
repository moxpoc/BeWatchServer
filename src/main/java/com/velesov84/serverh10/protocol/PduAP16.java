/*
 * AP16 - ответ устройства на пакет BP16
 * 
 * Устройство будет выполнять асинхронную 
 * передачу данных местоположения AP01 после ответа.
 */
package com.velesov84.serverh10.protocol;

/**
 *
 * @author Velesov84
 */
public class PduAP16 extends ProtocolDataUnit {
    
    public PduAP16(String aPacket)
    {
        super(aPacket);
    }
    
    public String getJournalNo()
    {
        return params[0];
    }    
}
