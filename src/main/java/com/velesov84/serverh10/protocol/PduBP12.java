/*
 * BP12 Set sos numbers(three)（respond：AP12）
 * Установить на часы 3 (или меньше) номера для экстренных случаев
 * 
 */
package com.velesov84.serverh10.protocol;

/**
 *
 * @author Velesov84
 */
public class PduBP12 extends PduBP0X {
    
    public PduBP12(String aIMEI)
    {
        super(aIMEI,"BP12",5);        
    }    
    
    public void setPhoneNumbers(String aPhone1, String aPhone2, String aPhone3)
    {
        params[2] = aPhone1;
        params[3] = aPhone2;
        params[4] = aPhone3;
    }        
}
