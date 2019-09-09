/*
 * AP84 - ответ часов на пакет BP84
 * 
 */
package com.velesov84.serverh10.protocol;

/**
 *
 * @author Velesov84
 */
public class PduAP84 extends PduAP16 {

    public PduAP84(String aPacket)
    {
        super(aPacket);
    }
    
    public boolean getCommandPerformed()
    {
        return params[1].charAt(0)=='1';
    }
    
}
