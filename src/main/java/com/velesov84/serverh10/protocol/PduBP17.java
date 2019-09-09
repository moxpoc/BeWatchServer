/*
 * BP17 Factory reset（respond：AP17）
 * ??? Сброс к заводским настройкам или сброс эффекта от BP16
 * 
 */
package com.velesov84.serverh10.protocol;

/**
 *
 * @author Velesov84
 */
public class PduBP17 extends PduBP0X {
    
    public PduBP17(String aIMEI)
    {
        super(aIMEI,"BP17",2);        
    }
    
}
