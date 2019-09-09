/*
 * AP12 - ответ устройства на BP12
 * Содержит номер журнала ответов и три 
 * (или меньше) установленных номера 
 */

package com.velesov84.serverh10.protocol;

/**
 *
 * @author Velesov84
 */
public class PduAP12 extends ProtocolDataUnit {
    
    public PduAP12(String aPacket)
    {
        super(aPacket);
    }
    
    public String getJournalNo()
    {
        return params[0];
    }
    
    public String[] getPhoneNumbers()
    {
        String[] result = { params[1], params[2], params[3] };        
        return result;        
    }        
    
}
