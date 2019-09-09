/*
 * AP07 Upload audio message（responds:BP07)
 * Загрузить аудио сообщение
 * 
 * Аудиоданные это последовательность байт, каждый пакет не может превышать 1024 байта. 
 * Последний пакет аудиоданных может быть меньше 1024 байта, если меньше 1024 байта, 
 * то длина = фактическому размеру
 * Правило для аудио загрузки:
 * 1: если устройство не получило ответы от сервера, оно будет повторять загрузку того же пакета аудиоданных
 * 2: если сервер ответит, что пакет получен успешно, устройство загрузит следующий пакет аудиоданных, 
 *    если текущая последовательность не достигнет общего номера пакета
 * 3: если сервер ответит, что получен сбой, устройство будет повторять загрузку того же пакета аудиоданных
 * 
 * В этой версии протокола поддерживается только AMR версия аудио
 */
package com.velesov84.serverh10.protocol;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Velesov84
 */
public class PduAP07 extends ProtocolDataUnit {
    
    public PduAP07(String aPacket)
    {
        super(aPacket);
    }
    
    public Date getDateTime()
    {
        Date result; 
        
        DateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
        try { result = f.parse(params[0]); } catch (Exception e) {
            System.out.println("Ошибка разбора строковой даты: "+e.toString());
            result = new Date();
        }
        
        return result;
    }
    
    public int getTotalPacketsCount()
    {
        return Integer.valueOf(params[1]);
    }
    
    public int getSequenceNumber()
    {
        return Integer.valueOf(params[2]);
    }
    
    public int getAudioDataLength()
    {
        return Integer.valueOf(params[3]);
    }        
    
    public byte[] getAudioData()
    {
        return params[4].getBytes();
    }    
}
