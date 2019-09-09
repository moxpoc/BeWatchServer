/*
 * AP33 - ответ часов на пакет BP33 
 */
package com.velesov84.serverh10.protocol;

/**
 *
 * @author Velesov84
 */
public class PduAP33 extends PduAP16 {
    
    public PduAP33(String aPacket)
    {
        super(aPacket);
    }
    
    public int getWorkingMode()
    {
        return Integer.valueOf(params[1]);
    }
    
}
