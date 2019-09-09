/*
 * Опорная точка сети GSM
 * используется для геолокации
 * 
 * 
 */
package com.velesov84.serverh10.protocol.utils;

/**
 *
 * @author Velesov84
 */
public class GsmBase {
    
    // Код области
    public int locationAreaCode;
    // Код ячейки (или вышки)
    public int cellID;
    // Уровень сигнала
    public int signalStrength;
    
    public GsmBase(String msg)
    {
        String rpMsg = msg.replace('|','!');
        String[] p = rpMsg.split("!");
        locationAreaCode = Integer.valueOf(p[0]);
        cellID = Integer.valueOf(p[1]);
        signalStrength = Integer.valueOf(p[2]);
    }
}
