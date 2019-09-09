/*
 * APHP Upload heart rate, BP, SaO2, blood sugar（responds：BPHP）
 * Сообщает частоту сердцебиения, давление, 
 * SaO2 (уровень насыщения крови кислородом) и уровень сахара
 * 
 */
package com.velesov84.serverh10.protocol;

/**
 *
 * @author Velesov84
 */

public class PduAPHP extends PduAPHT {
    
    public PduAPHP(String aPacket)
    {
        super(aPacket);
    }
    
    public int getSaO2()
    {
        return Integer.valueOf(params[3]);
    }
    
    public int getBloodSugar()
    {
        return Integer.valueOf(params[4]);
    }
    
}
