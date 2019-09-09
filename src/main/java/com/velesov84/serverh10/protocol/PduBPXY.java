/*
 * BPXY  Test blood pressure（respond：APXY）
 * Проверка давления крови
 */
package com.velesov84.serverh10.protocol;

/**
 *
 * @author Velesov84
 */
public class PduBPXY extends PduBP0X {
    
    public PduBPXY(String aIMEI)
    {
        super(aIMEI,"BPXY",2);        
    }
    
}
