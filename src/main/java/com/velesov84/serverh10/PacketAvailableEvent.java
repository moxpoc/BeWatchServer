/*
 * Событие возникает при поступлении от устройства
 * нового пакета данных
 */
package com.velesov84.serverh10;

import com.velesov84.serverh10.protocol.ProtocolDataUnit;

import java.util.EventObject;

/**
 *
 * @author Velesov84
 */
public class PacketAvailableEvent extends EventObject {
    
    private ProtocolDataUnit pdu;
    
    public PacketAvailableEvent(Object source, ProtocolDataUnit aPDU)
    {
        super(source);
        pdu = aPDU;
    }
    
    public PacketAvailableEvent(Object source)
    {
        this(source,null);
    }
    
    public ProtocolDataUnit getPDU()
    {
        return pdu;
    }
    
    @Override
    public String toString()
    {
        return getClass().getName() + "[source = " + getSource() + ", PDU = " + pdu.toPacket() + "]";
    }

    
}
