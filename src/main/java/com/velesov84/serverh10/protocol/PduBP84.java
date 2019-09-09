/*
 * BP84 Switch for White list（respond：AP84）
 * Переключает "белый" список контактов
 * 
 */
package com.velesov84.serverh10.protocol;

/**
 *
 * @author Velesov84
 */
public class PduBP84 extends PduBP0X {
    
    public PduBP84(String aIMEI)
    {
        super(aIMEI,"BP84",3);        
    }
    
    public void setWhiteListEnabled(boolean Value)
    {
        params[2] = Value ? "1":"0";
    }
            
    
}
