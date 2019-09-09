/*
 * Очередь объектов от устройства. 
 * Может содержать пакеты протокола H10
 * и аудио данные
 * 
 * Существует в единственном экземпляре.
 * 
 */
package com.velesov84.serverh10;

import com.velesov84.serverh10.protocol.ProtocolDataUnit;
import com.velesov84.serverh10.protocol.utils.AudioDataStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;

/**
 *
 * @author Velesov84
 */

class DeviceQueue {
    
    // Очередь входящих пакетов (для генерирования событий)
    private ArrayDeque<Object> fromDevice;
    private static DeviceQueue instance;
    private static Logger mLog = LogManager.getLogger(DeviceQueue.class);
    
    public DeviceQueue()
    {
        fromDevice = new ArrayDeque();
    }
    
    public static synchronized DeviceQueue getInstance()
    {
        if (instance==null) { instance = new DeviceQueue(); }
        return instance;
    }        
    
    // Добавить пакет в очередь "от устройства"
    
    public synchronized void postObjectFromDevice(Object Value)
    {
        fromDevice.addFirst(Value);
        mLog.info("Пакет добавлен в очередь `от устройства`");
        String s = Value instanceof ProtocolDataUnit ? ((ProtocolDataUnit)Value).toPacket() : "это аудио данные";
        mLog.info(s);
    }
    
    // Извлечь объект из очереди "от устройства" для генерации события
    
    public synchronized Object getObjectToMaster()
    {
        Object result = fromDevice.pollLast();
        if (result!=null) {
            mLog.info("Объект извлечен из очереди `от устройства`");
            if (result instanceof AudioDataStore) {
                mLog.info("это аудио данные");
            } else {
               mLog.info(((ProtocolDataUnit)result).toPacket());
            }   
        }
        return result;
    }
}
