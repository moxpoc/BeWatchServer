/*
 * Объект телефонного контакта
 * Используется в пакетах BP14, BP12  
 */
package com.velesov84.serverh10.protocol.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 *
 * @author Velesov84
 */
public class ContactEntry {
    
    public String name;
    public String phone;
    
    public ContactEntry(String aName, String aPhone)
    {
        name = aName;
        phone = aPhone;
    }        
    
    public ContactEntry()
    {
        name = "";
        phone = "";
    }
    
    public String encode()
    {
        String result;
        
        try {
            result = Hex.fromBytes(name.getBytes("UTF-16"))+"|"+phone;
            result = result.substring(18);
        } catch (Exception e) {
            System.out.println("Ошибка кодирования строки: "+e.toString());
            result = name+"|"+phone;
        }    
        return result;
    }
    
    public void decode(String aEntry)
    {
        String rpMsg = aEntry.replace('|','!');
        String[] p = rpMsg.split("!");
        byte[] s = Hex.toBytes(p[0]);
        try {
            name = new String(s,"UTF-16");
        } catch (UnsupportedEncodingException e) {
            name = new String(s,Charset.defaultCharset());
        }    
        phone = p[1];        
    }
}
