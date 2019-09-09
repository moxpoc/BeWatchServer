/*
 * BP33 Working Mode（respond：AP33）
 * Установка режима работы устройства
 * !!! Часы передают GPS даные только находясь в EMERGENCY_MODE
 */
package com.velesov84.serverh10.protocol;

/**
 *
 * @author Velesov84
 */
public class PduBP33 extends PduBP0X {
    
    // Нормальный режим - каждые 15 минут отчет о местонахождении
    // с помощью WIFI и LBS
    public static final int NORMAL_MODE = 1;
    // Режим энергосбережения - то же, что и NORMAL_MODE, 
    // но период отчета - 60 мин.
    public static final int POWER_SAVING_MODE = 2;
    // Режим ЧС - каждую минуту отчет с помощью GPS, WIFI и LBS
    public static final int EMERGENCY_MODE = 3;    
    
    public PduBP33(String aIMEI)
    {
        super(aIMEI,"BP33",3);        
    }
    
    public void setWorkingMode(int Value)
    {
        params[2] = Integer.toString(Value);
    }    
}
