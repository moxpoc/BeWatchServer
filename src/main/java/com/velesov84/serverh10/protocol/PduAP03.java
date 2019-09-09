/*
 * AP03 Heartbeat package(responds:BP03)
 * Сообщение сердцебиения. Устройство может использовать это сообщение 
 * для поддержания длительного соединения с сервером. 
 */
package com.velesov84.serverh10.protocol;

import com.velesov84.serverh10.protocol.utils.*;

/**
 *
 * @author Velesov84
 */

public class PduAP03 extends ProtocolDataUnit {
    
    public PduAP03(String aPacket)
    {
        super(aPacket);
    }
    
    public DeviceStatus getGsmData()
    {
        return new DeviceStatus(params[0]);        
    }
    
    public int getStepsCount()
    {
        return Integer.valueOf(params[1]);
    }
    
    public int getRollsFrequency()
    {
        return Integer.valueOf(params[2]);
    }
    
}
