/*
 * Интерфейс слушателя событий сервера H10  
 */
package com.velesov84.serverh10;

/**
 *
 * @author Velesov84
 */
public interface DataAvailableListener {
    
    // От устройства поступил новый пакет
    public void packetDataAvailable(PacketAvailableEvent e);
    // От устройства поступили аудио-данные в формате amr
    public void audioDataAvailable(AudioAvailableEvent e);
}
