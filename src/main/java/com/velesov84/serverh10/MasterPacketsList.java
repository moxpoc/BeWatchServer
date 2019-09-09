/*
 * Список пакетов, поставленых на отправку
 * мастером - REST-сервером или другим приложением
 * Существует в единственном экземпляре
 * 
 */
package com.velesov84.serverh10;

import com.velesov84.serverh10.protocol.ProtocolDataUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Velesov84
 */
class MasterPacketsList {
    
    // Список пакетов от REST сервера (на отправку)
    private CopyOnWriteArrayList<ProtocolDataUnit> fromMaster;    
    private static MasterPacketsList instance;
    private static Logger mLog = LogManager.getLogger(MasterPacketsList.class);
    
    public MasterPacketsList()
    {
        fromMaster = new CopyOnWriteArrayList();        
    }
    
    public static synchronized MasterPacketsList getInstance()
    {
        if (instance==null) { instance = new MasterPacketsList(); }
        return instance;
    }        
    
    // Добавить пакет в список "от мастер-сервера"

    public synchronized void postPduFromMaster(ProtocolDataUnit Value)
    {
        fromMaster.add(Value);
        Collections.sort(fromMaster);
        mLog.info("Пакет добавлен в список `от мастера`");
        mLog.info(Value.toPacket());
    }
    
    // Извлечь все пакеты для устройства с указанным IMEI из списка "от мастер-сервера" для отправки
    
    public synchronized ArrayList<ProtocolDataUnit> getPduListToDevice(String aIMEI)
    {
        if (fromMaster.size()==0) { return null; }
        
        int[] selected = new int[fromMaster.size()];        
        ArrayList<ProtocolDataUnit> result = new ArrayList<ProtocolDataUnit>();        
        int i = 0; int count = 0;        
        
        for (i=0; i<fromMaster.size(); i++) {
            ProtocolDataUnit current = fromMaster.get(i);
            if (aIMEI.equals(current.getIMEI())) {
                selected[count] = i;
                result.add(current);
                count++;
            }
        }        
        for (i=0; i<count; i++) { fromMaster.remove(selected[i]); }                
        Collections.sort(fromMaster);
        if (result.size()!=0) { mLog.info("... пакеты извлечены из списка `от мастера`"); }
        return result;        
    }    
}
