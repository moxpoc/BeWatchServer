/*
 * Опорная точка блютуз
 * Используется в пакетах геолокации  
 */
package com.velesov84.serverh10.protocol.utils;

/**
 *
 * @author Velesov84
 */
public class BluetoothBase {
    
    public String btName;
    public String mac;
    public int signalStrength;
    
    public BluetoothBase(String msg)
    {
        String rpMsg = msg.replace('|','!');
        String[] p = rpMsg.split("!");
        btName = p[0];
        mac = p[1];
        signalStrength = Integer.valueOf(p[2]);
    }
}
