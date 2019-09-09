/*
 * BP16 Real-time locating command（respond：AP16）
 * Команда определения местопоожения в реальном времени
 * 
 */
package com.velesov84.serverh10.protocol;

/**
 *
 * @author Velesov84
 */
public class PduBP0X extends ProtocolDataUnit {
    
    public PduBP0X(String aIMEI, String aCommandID, int aParamsCount)
    {
        super(aIMEI,aCommandID,aParamsCount);        
        params[0] = aIMEI;        
    }
    
    @Override
    public void setIMEI(String Value)
    {
        IMEI = Value;
        params[0] = IMEI;
    }
    
    public void setJournalNo(String Value)
    {
        params[1] = Value;
    }
    
}
