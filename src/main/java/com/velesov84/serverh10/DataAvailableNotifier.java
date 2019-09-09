/*
 * Поток, сообщающий слушателям о событиях сервера.
 * Запускается сервером перед стартом ожидания соединений.
 * Существует в единственном экземпляре  
 */
package com.velesov84.serverh10;

import com.velesov84.serverh10.protocol.ProtocolDataUnit;
import com.velesov84.serverh10.protocol.utils.AudioDataStore;

import java.util.ArrayList;

/**
 *
 * @author Velesov84
 */

// singleton

class DataAvailableNotifier extends Thread {
    
    private DeviceQueue packetQueue;
    private ArrayList<DataAvailableListener> listeners;
    private static DataAvailableNotifier instance;
    
    public DataAvailableNotifier()
    {
        packetQueue = DeviceQueue.getInstance();
        listeners = new ArrayList();
        setName("notifier-thread");
        setPriority(MIN_PRIORITY);
        setDaemon(false);
    }        
    
    public static synchronized DataAvailableNotifier getInstance()
    {
        if (instance==null) { instance = new DataAvailableNotifier(); }
        return instance;
    }        
    
    // Методы управления подпиской на события
    
    public synchronized void addDataAvaivableEventListener(DataAvailableListener listener)
    { 
        synchronized(listeners) { listeners.add(listener); }
    }
    
    public synchronized DataAvailableListener[] getDataAvaivableEventListeners()
    {
        synchronized(listeners) {
            return listeners.toArray(new DataAvailableListener[listeners.size()]);
        }
    }
    
    public synchronized void removeDataAvaivableEventListener(DataAvailableListener listener)
    {
        synchronized(listeners) { listeners.remove(listener); }
    }        
    
    // методы оповещения о событиях
    
    protected synchronized void firePacketAvaivableEvent(ProtocolDataUnit pdu)
    {
        PacketAvailableEvent event = new PacketAvailableEvent(this,pdu);
        
        synchronized(listeners) {
            for (DataAvailableListener listener : listeners) {
                listener.packetDataAvailable(event);
            }
        }    
    }
    
    protected synchronized void fireAudioAvaivableEvent(AudioDataStore audio)
    {
        AudioAvailableEvent event = new AudioAvailableEvent(this,audio);
        
        synchronized(listeners) {
            for (DataAvailableListener listener : listeners) {
                listener.audioDataAvailable(event);
            }
        }    
    }
    
    // Главный рабочий метод потока
    
    @Override
    public void run()
    {
        while (!isInterrupted()) {
            // извлекаем очередной пакет от устройства и,
            // если он есть - генерируем событие
            Object inObj = packetQueue.getObjectToMaster();
            if (inObj!=null) { 
            // Тип генерируемого события зависит от класса объекта, извлеченного из очереди    
                if (inObj instanceof ProtocolDataUnit) {
                    firePacketAvaivableEvent((ProtocolDataUnit)inObj);
                } else {
                    fireAudioAvaivableEvent((AudioDataStore)inObj);
                }    
            }
            try { sleep(100); } catch (Exception e) { break; }
        }
    }        
    
}
