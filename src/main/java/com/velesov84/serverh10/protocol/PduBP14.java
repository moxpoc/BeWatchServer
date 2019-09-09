/*
 * BP14 Set white list(Phone book) numbers(ten)（respond：AP14)
 * 
 * 
 */
package com.velesov84.serverh10.protocol;

import com.velesov84.serverh10.protocol.utils.ContactEntry;

import java.util.ArrayList;

/**
 *
 * @author Velesov84
 */
public class PduBP14 extends PduBP0X {

    public PduBP14(String aIMEI)
    {
        super(aIMEI,"BP14",12);        
    }
    
    public void setContacts(ArrayList<ContactEntry> Value)
    {
        int cCount = Value.size();        
        if (cCount==0) { return; }
        if (cCount>10) { cCount=10; }
        int i; ContactEntry c;
        
        for (i=0; i<cCount; i++) {
            c = Value.get(i);
            params[i+2] = c.encode();
        }
        //if (cCount<10) for (i=cCount; i<10; i++) { params[i+2] = ""; }        
    }
}
