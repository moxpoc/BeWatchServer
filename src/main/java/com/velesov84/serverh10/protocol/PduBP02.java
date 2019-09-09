/*
 * BP02 - ответ сервера на сообщение AP02.
 * BP02 должен содержвть в себе человеко-понятный адрес на языке 
 * AP02.getDeviceLanguage, если этот адрес запрашивается в AP02 (addressNeeded: true)
 */
package com.velesov84.serverh10.protocol;

import com.velesov84.serverh10.protocol.utils.Hex;

/**
 *
 * @author Velesov84
 */
public class PduBP02 extends ProtocolDataUnit {
    
    public PduBP02(String aIMEI, String aCommandID)
    {
        super(aIMEI,aCommandID,0);
    }
    
    public void setAddress(String address)
    {
        params = new String[1];
        if (address.length()>0) try {
            byte[] code = address.getBytes("UTF-16");
            params[0] = Hex.fromBytes(code);
        } catch (Exception e) {
            System.out.println("Ошибка кодирвания: "+e.toString());
        }
    }
    
}
