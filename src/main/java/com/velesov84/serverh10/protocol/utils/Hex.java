/*
 * Вспомогательный класс для преобразования
 * текстовых строк в hex-строки  
 */
package com.velesov84.serverh10.protocol.utils;

/**
 *
 * @author Velesov84
 */
public class Hex {
    
    public static String fromBytes(byte[] aBytes) {

        String s = "";
        String n;
        
        for (byte b : aBytes) {
            n = b<0x10 ? "0" : "";
            s += n+Integer.toHexString(b);            
        }
        return s.toUpperCase();
    }
    
    public static byte[] toBytes(String hexStr) 
        throws NumberFormatException
    {
        int i,j1,j0,m;
        
        String msg = hexStr.toUpperCase();
        String p = "0123456789ABCDEF";
        m = hexStr.length()/2;
        byte[] b = new byte[m];
        m = 0; char c1,c0;
        
        for (i=0; i<msg.length(); i+=2) {
            c1 = msg.charAt(i);
            c0 = msg.charAt(i+1);
            j1 = p.indexOf(c1);
            j0 = p.indexOf(c0);
            if ((j0<0)||(j1<0)) { 
                throw new NumberFormatException("Строка не является представлением шестнадцатеричного числа.");
            }
            b[m] = (byte) ((j1<<4)|j0);
            m++;
            if (m>=b.length) { break; }
        }
        return b;        
    }
}    
