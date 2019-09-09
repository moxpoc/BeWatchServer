/*
 * Объект-хранилище аудио данных  
 */

package com.velesov84.serverh10.protocol.utils;

import com.velesov84.serverh10.protocol.PduAP07;
import com.velesov84.serverh10.protocol.PduBP28;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 *
 * @author Velesov84
 */
public class AudioDataStore {
    
    private byte[] data;
    private int totalPackets;
    private int receivedPackets;
    private int offset;
    private static Logger ioLog = LogManager.getRootLogger();
    
    public AudioDataStore(PduAP07 aFirstPacket)
    {
        offset = 0;
        receivedPackets = 0;
        totalPackets = aFirstPacket.getTotalPacketsCount();
        data = new byte[1024*totalPackets];        
    }
    
    public AudioDataStore(byte[] audio)
    {
        offset = 0;
        receivedPackets = 0;
        totalPackets = getPacketsCount(audio.length);
        data = audio;
    }
    
    public AudioDataStore()
    {
        offset = 0;
        receivedPackets = 0;
        totalPackets = 0;        
    }
    
    // Рассчет количества пакетов из длины данных
    
    private static int getPacketsCount(int dataLength)
    {
        int m = dataLength%1024;
        int c = m>0 ? 1:0;
        return (dataLength / 1024) + c;
    }        
    
    // Добавить в хранилище данные из очередного пакета от устройства
    // Вход: Объект аудио-пакета от устройства
    // Выход: Истина, если получен последний пакет и хранилище заполнено
    
    public boolean addDevicePacket(PduAP07 aPacket)
    {
        int seqNumber = aPacket.getSequenceNumber();                                
        int dataLength = aPacket.getAudioDataLength();
        
        System.arraycopy(aPacket.getAudioData(),0,data,offset,dataLength);
        offset += dataLength;
        receivedPackets = seqNumber;
        return receivedPackets==totalPackets;
    }
    
    // Разбить заполненное хранилище на массив исходящих пакетов
    // для отправки на устройство
    // Вход: 
    //   IMEI получателя, Имя отправителя, 
    //   Доп. параметры (длина не больше 4 символов - это все, что про них известно)
    // Выход:
    //   Массив пакетов для отправки на устройство
    
    public PduBP28[] getOutcomingPackets(String forIMEI, String senderName, String additional)
    {
        PduBP28[] result = new PduBP28[totalPackets];
        int i,dataLength;  byte[] chunk = new byte[1024];        
        
        for (i=0; i<totalPackets; i++) {
            result[i] = new PduBP28(forIMEI);
            dataLength = i<(totalPackets-1) ? 1024 : data.length%1024;
            System.arraycopy(data,offset,chunk,0,dataLength);
            offset += dataLength;            
            result[i].setSequenceNumber(i+1);
            result[i].setTotalPacketsCount(totalPackets);
            result[i].setAudioData(chunk,dataLength);
            result[i].setSenderName(senderName);
            result[i].setAdditionalData(additional);            
        }
        return result;
    }
    
    // Длина аудио данных в хранилище
    
    public int getLength() 
    { 
        return data.length%1024==0 ? offset : data.length;
    }
    
    // Данные хранилища. 
    // Массив может быть не полностью заполненным, поэтому действительная длина
    // данных отлична от длины массива
    
    public byte[] getData() 
    { 
        return data; 
    }
    
    // Сохранить аудио в файл
    // (часы понимают только формат AMR, поэтому сохранять 
    // лучше в файлы с расширением amr, чтобы не было путаницы)
    
    public void saveToFile(String fileName)
    {
        try {
            FileOutputStream out = new FileOutputStream(new File(fileName));
            out.write(data,0,getLength());
            out.close();
        } catch (Exception e) {
            ioLog.debug("Ошибка сохранения аудио в файл: "+e.toString());
        }
    }
    
    // Загрузить аудио из файла
    
    public void loadFromFile(String fileName)
    {
        try {
            FileInputStream in = new FileInputStream(new File(fileName));
            data = new byte[in.available()];
            in.read(data);
            in.close();
            offset = 0; receivedPackets = 0;
            totalPackets = getPacketsCount(data.length); 
        } catch (Exception e) {
            ioLog.debug("Ошибка чтения аудио из файла: "+e.toString());
        }
    }        
}
