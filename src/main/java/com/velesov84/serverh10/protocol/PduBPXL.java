/*
 * BPXL Test heart rate（respond：APXL）
 * Проверка частоты сердцебиения
 * 
 */
package com.velesov84.serverh10.protocol;

/**
 *
 * @author Velesov84
 */
public class PduBPXL extends PduBP0X {
    
    public PduBPXL(String aIMEI)
    {
        super(aIMEI,"BPXL",2);        
    }   
}
