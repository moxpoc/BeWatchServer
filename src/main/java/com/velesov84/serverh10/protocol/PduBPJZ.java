/*
 * BPJZ  Blood pressure calibration（respond：APJZ）
 * Калибровка артериального давления
 * 
 */
package com.velesov84.serverh10.protocol;

/**
 *
 * @author Velesov84
 */
public class PduBPJZ extends PduBP0X {
    
    public PduBPJZ(String aIMEI)
    {
        super(aIMEI,"BPJZ",4);        
    }
    
    public void setSystolicPressure(int Value)
    {
        params[2] = Integer.toString(Value);
    }
    
    public void setDiastolicPressure(int Value)
    {
        params[3] = Integer.toString(Value);
    }
}
