/*
 * Событие возникает когда с устройства (часов)
 * полностью загружен аудио поток.
 * Аудио сохраняется в объекте AudioDataStore, который
 * передается обработчику события
 * 
 */
package com.velesov84.serverh10;

import com.velesov84.serverh10.protocol.utils.AudioDataStore;

import java.util.EventObject;

/**
 *
 * @author Velesov84
 */
public class AudioAvailableEvent extends EventObject {
    
    private AudioDataStore data;
    
    public AudioAvailableEvent(Object source, AudioDataStore aData)
    {
        super(source);
        data = aData;
    }
    
    public AudioAvailableEvent(Object source)
    {
        this(source,null);
    }
    
    public AudioDataStore getAudio()
    {
        return data;
    }
    
    @Override
    public String toString()
    {
        return getClass().getName() + "[source = " + getSource() + ", audio data is byte array]";
    }
    
}
