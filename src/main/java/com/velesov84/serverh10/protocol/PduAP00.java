/*
 * AP00-Login package(responds:BP00)
 * Пакет входа отправляется каждый раз, когда устройство подключается к серверу.
 */
package com.velesov84.serverh10.protocol;

/**
 *
 * @author Velesov84
 */
public class PduAP00 extends ProtocolDataUnit {
    
    public PduAP00(String aPacket)
    {
        super(aPacket);
    }
    
    @Override
    public String getIMEI()
    {
        return params[0];
    }
    
    @Override
    public void setIMEI(String Value)
    {
        IMEI = Value;        
        params[0] = Value;
    }        
    
}
