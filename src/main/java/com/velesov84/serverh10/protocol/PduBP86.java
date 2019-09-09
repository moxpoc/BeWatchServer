/*
 * BP86 Set the interval of heart rate auto testing（respond：AP86）
 * Установка интервала автоматического тестирования сердечного ритма
 * или давления крови
 */
package com.velesov84.serverh10.protocol;

/**
 *
 * @author Velesov84
 */
public class PduBP86 extends PduBP0X {
    
    public static final int TESTING_HEART_RATE = 1;
    public static final int TESTING_BLOOD_PRESSURE = 2;
    
    public PduBP86(String aIMEI)
    {
        super(aIMEI,"BP86",4);        
    }
    
    public void setTestingType(int Value)
    {
        params[2] = String.valueOf(Value);
    }
    
    public void setInterval(int Minutes)
    {
        params[3] = String.valueOf(Minutes);
    }
    
}
