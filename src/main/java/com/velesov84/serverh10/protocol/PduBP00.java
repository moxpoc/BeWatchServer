/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.velesov84.serverh10.protocol;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Velesov84
 */
public class PduBP00 extends ProtocolDataUnit {
    
    public PduBP00(String aIMEI)
    {
        super(aIMEI,"BP00",2);        
    }
    
    public void setDateTime(Date Value)
    {
        DateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
        params[0] = f.format(Value);
    }
    
    public Date getDateTime() throws ParseException
    {
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date mDate = format.parse(params[0]);
        return mDate;
    }
    
    public void setTimeZone(int Value)
    {
        params[1] = Integer.toString(Value);
    }
    
    public int getTimeZone()
    {
        return Integer.valueOf(params[1]);
    }
}
