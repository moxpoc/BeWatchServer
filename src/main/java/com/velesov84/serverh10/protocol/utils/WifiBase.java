/*
 * Опорная точка wifi
 * используется в пакетах геолокации
 */
package com.velesov84.serverh10.protocol.utils;

/**
 *
 * @author Velesov84
 */
public class WifiBase {
    
    public String ssid;
    public String mac;
    public int signalStrength;
    
    public WifiBase(String msg)
    {
        String rpMsg = msg.replace('|','!');
        String[] p = rpMsg.split("!",3);
        ssid = p[0];
        mac = p[1];
        signalStrength = Integer.valueOf(p[2]);
    }
}
