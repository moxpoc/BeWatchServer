/*
 * AP14 - ответ устройства на пакет BP14
 */
package com.velesov84.serverh10.protocol;

import com.velesov84.serverh10.protocol.utils.ContactEntry;

import java.util.ArrayList;

/**
 *
 * @author Velesov84
 */

public class PduAP14 extends ProtocolDataUnit {
    
    public PduAP14(String aPacket)
    {
        super(aPacket);
    }
    
    
    
    public String getJournalNo()
    {
        return params[0];
    }
    
    public ArrayList<ContactEntry> getContacts()
    {
        int i; ContactEntry c;
        ArrayList<ContactEntry> result = new ArrayList();
        
        for (i=1; i<params.length; i++) {
            c = new ContactEntry();
            c.decode(params[i]);
            result.add(c);
        }
        return result;
    }        

    
}
