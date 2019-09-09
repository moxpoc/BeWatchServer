/*
 * Статус устройства.
 * При установке нормального соединения
 * часы периодически отправляют пакет AP03 
 * со статусом устройства
 * 
 */
package com.velesov84.serverh10.protocol.utils;

/**
 *
 * @author Velesov84
 */
public class DeviceStatus {
    
    // Уровень сигнала сотовой сети (%)
    public int gsmSignalLevel;
    // Количество доступных спутников GPS
    // Если 0, то часы не смогут сообщить правильные GPS координаты
    public int numberOfGpsSattelites;
    // Уровень заряда батареи (%)
    public int battaryLevel;
    // ??? Оставшееся место на носителе
    public int remainingSpace;
    // ???
    public int fortificationState;
    // ??? Режим работы
    public int workingMode;
    
    public DeviceStatus(String msg)
    {
        String dt;
            
        dt = msg.substring(0,3);
        gsmSignalLevel = Integer.valueOf(dt);
        dt = msg.substring(3,6);
        numberOfGpsSattelites = Integer.valueOf(dt);
        dt = msg.substring(6,9);
        battaryLevel = Integer.valueOf(dt);
        dt = msg.substring(9,10);
        remainingSpace = Integer.valueOf(dt);
        dt = msg.substring(10,12);
        fortificationState = Integer.valueOf(dt);
        dt = msg.substring(12,14);
        workingMode = Integer.valueOf(dt);
    }
}
