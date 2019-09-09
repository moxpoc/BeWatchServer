/*
 * APHT Upload heart rate and BP（responds：BPHT）
 * Сообщает частоту сердцебиения и давление крови
 * 
 */
package com.velesov84.serverh10.protocol;

/**
 *
 * @author Velesov84
 */
public class PduAPHT extends ProtocolDataUnit {
    
    public PduAPHT(String aPacket)
    {
        super(aPacket);
    }
    
    public int getHeartRate()
    {
        return Integer.valueOf(params[0]);
    }
    
    public int getSystolicPressure()
    {
        return Integer.valueOf(params[1]);
    }
    
    public int getDiastolicPressure()
    {
        return Integer.valueOf(params[2]);
    }
    
}
