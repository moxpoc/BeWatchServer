/*
 * AP49 Upload heart rate（responds：BP49）
 * Сообщает частоту сердцебиения
 * 
 */
package com.velesov84.serverh10.protocol;

/**
 *
 * @author Velesov84
 */
public class PduAP49 extends ProtocolDataUnit {
    
    public PduAP49(String aPacket)
    {
        super(aPacket);
    }
    
    public int getHeartRate()
    {
        return Integer.valueOf(params[0]);
    }
    
}
