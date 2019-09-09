/*
 * BP10 - ответ сервера на сообщение AP10.
 * BP10 должен содержвть в себе человеко-понятный адрес на языке 
 * AP10.getDeviceLanguage, если этот адрес запрашивается в AP10 (addressNeeded: true)  
 */
package com.velesov84.serverh10.protocol;

/**
 *
 * @author Velesov84
 */
public class PduBP10 extends PduBP02 {
    
    public PduBP10(String aIMEI)
    {
        super(aIMEI,"BP10");
    }
    
    public void setAddressEx(String aAddress, String aHyperlink)
    {
        String msg = aAddress+'\n'+aHyperlink;
        setAddress(msg);
    }
    
}
