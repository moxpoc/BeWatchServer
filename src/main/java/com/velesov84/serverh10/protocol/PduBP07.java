/*
 * BP07 - ответ сервера на пакет AP07
 * Сообщает об успехе или провале доставки
 * аудио-пакета AP07
 * 
 */
package com.velesov84.serverh10.protocol;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Velesov84
 */
public class PduBP07 extends ProtocolDataUnit {
    
    public PduBP07(String aIMEI)
    {
        super(aIMEI,"BP07",4);        
    }
    
    public void setDateTime(Date Value)
    {
        DateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
        params[0] = f.format(Value);
    }
    
    public void setTotalPacketsCount(int Value)
    {
        params[1] = Integer.toString(Value);
    }
    
    public void setSequenceNumber(int Value)
    {
        params[2] = Integer.toString(Value);
    }
    
    public void setSuccess(boolean Value)
    {
        if (Value) { params[3] = "1"; } else { params[3] = "0"; }
    }        
    
    
    
}
