/*
 * Поток-обработчик подключений
 * В соответствии с протоколом автоматически отвечает на входящие пакеты
 * Ставит входящие PDU в очередь сообщений о событиях
 * Отправляет исходящие PDU из очереди на отправку
 * 
 */
package com.velesov84.serverh10;

import com.bewatches.server.Model.App.Blood;
import com.bewatches.server.Model.App.Watch;
import com.bewatches.server.Model.Parent.BeatHeart;
import com.bewatches.server.Model.Parent.Location;
import com.velesov84.serverh10.protocol.*;
import com.velesov84.serverh10.protocol.utils.DeviceStatus;
import com.velesov84.serverh10.protocol.utils.AudioDataStore;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import com.velesov84.serverh10.protocol.utils.GpsData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;


/**
 *
 * @author Velesov84
 */

class NodeWorker extends Thread {
    
    private ImeiTable imeiTable;
    private ImeiTableEntry addr;
    private DeviceQueue deviceQueue;    
    private MasterPacketsList masterList;    
    private Socket s;
    private InputStream inStream;
    private OutputStream outStream;
    private AtomicBoolean longConnect,extractorActive;
    private long kaStartTime, wrStartTime;
    private String expectedResponse;
    private AudioDataStore uploadedAudio;
    
    private static Logger infoLog = LogManager.getLogger(NodeH10.class);


    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("NodeWorker");

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public NodeWorker(Socket aSocket)
    {
        deviceQueue = DeviceQueue.getInstance();        
        masterList = MasterPacketsList.getInstance();
        imeiTable = ImeiTable.getInstance();
        longConnect = new AtomicBoolean(false);
        extractorActive = new AtomicBoolean(true);
        s = aSocket; expectedResponse = null;
        
        String mName = getName();
        String[] p = mName.split("-");
        mName = "worker-thread-"+p[1];
        setName(mName);        
        
        try {
            inStream = s.getInputStream();
            outStream = s.getOutputStream();
        } catch (Exception e) {
            infoLog.debug("Ошибка создания потоков данных сокета: "+e.toString());
        }
        addr = new ImeiTableEntry();
        addr.IP = s.getRemoteSocketAddress().toString();
        addr.IMEI = imeiTable.getImeiByIP(addr.IP).substring(0,15);
        
        setDaemon(false);
        setPriority(NORM_PRIORITY);        
        start();
    }
    
    
    // Диспетчер входящих пакетов
    // Помещает все входящие пакеты в очередь событий
    // Формирует ответный пакет в соответствии с протоколом
    // Вход: ОбъектПакетаОтУстройства
    // Выход: ОбъектОтветногоПакета
    // Если на пакет не требуется ответ, то возвращает null
    
    private ProtocolDataUnit DispatchPacket(ProtocolDataUnit inPDU)
    {
        if (inPDU==null) { return null; }        
        String aCommand = inPDU.getCommandID().toUpperCase();
        kaStartTime = System.currentTimeMillis();
        deviceQueue.postObjectFromDevice(inPDU);
        
        if (expectedResponse!=null) {
            if (expectedResponse.equals(inPDU.getCommandID())) { 
                expectedResponse = null; 
            }            
        }
        
        switch (aCommand) {
            case "AP00": { 
                // >>Есть IMEI                
                PduAP00 inObj = (PduAP00) inPDU;                
                addr.IMEI = inObj.getIMEI();
                imeiTable.modifyIpByImei(addr);
                infoLog.info(">> IP "+addr.IP+" представился как IMEI "+addr.IMEI);                
                PduBP00 result = (PduBP00) ProtocolManager.buildDataUnit(addr.IMEI, "BP00");
                result.setDateTime(new Date());
                result.setTimeZone(NodeSettings.TIME_ZONE);


                BeatHeart beatHeart = new BeatHeart(addr.IMEI, 0,0);
                Blood blood = new Blood(addr.IMEI,0,0, 0);
                Location location = new Location(addr.IMEI,"gmap", "0", "0");
                Watch watch = new Watch(addr.IMEI, "nulled", 0, 0, 0, 0, "nulled", "nulled", 0, 0, 0, 0, "nulled", "nulled", "nulled", "nulled");
                watch.setBeatHeart(beatHeart);
                watch.setBlood(blood);
                watch.setLocation(location);

                EntityManager em = getEntityManager();
                em.getTransaction().begin();
                Long tryWatch = 0L;
                try {
                    tryWatch = (Long)em.createQuery("SELECT count(imei) FROM watch WHERE imei = :watchImei")
                            .setParameter("watchImei", addr.IMEI).getSingleResult();
                    System.out.println("LOGIN QUERY ======= " + tryWatch);
                } catch(NoResultException e){
                    e.printStackTrace();
                } catch (NonUniqueResultException e){
                    e.printStackTrace();
                }
                if(tryWatch.equals(0L)){
                    em.persist(watch);
                    em.getTransaction().commit();
                    System.out.println("SUCCESS PERSIST IMEI = " + addr.IMEI);
                }
                em.clear();
                return result; 
            }
            case "AP01": {
                ProtocolDataUnit result = ProtocolManager.buildDataUnit(addr.IMEI, "BP01");
                PduAP01 ap01 = (PduAP01) inPDU;
                GpsData gpsData = ap01.getGPS();
                Location location = new Location(addr.IMEI,
                        "sometype",
                        String.valueOf(gpsData.getMapsLatitude()),
                        String.valueOf(gpsData.getMapsLongitude()));

                //DeviceStatus deviceStatus = ap01.getDeviceStatus();

                EntityManager em = getEntityManager();
                em.getTransaction().begin();
                Long ress = 0L;
                try {
                    ress = (Long)em.createQuery("SELECT count(imei) FROM Location WHERE imei = :locationImei")
                            .setParameter("locationImei", location.getImei()).getSingleResult();
                    System.out.println(ress.toString());
                } catch (Exception e){
                    e.printStackTrace();
                }
                if(!ress.equals(0L)){
                    em.createQuery("UPDATE Location SET lat = :latitude, lon = :longitude WHERE imei = :locationImei")
                            .setParameter("latitude", location.getLat())
                            .setParameter("longitude", location.getLon())
                            .setParameter("locationImei", location.getImei()).executeUpdate();
                }

                //em.merge(location);

                em.getTransaction().commit();
                em.clear();
                System.out.println("___NODE___WORKER_HERE___");
                return result;
            }
            case "AP02": {
                PduBP02 result = (PduBP02) ProtocolManager.buildDataUnit(addr.IMEI,"BP02");
                return result;                 
            }
            case "AP03": {
                PduAP03 inObj = (PduAP03) inPDU; 
                DeviceStatus status = inObj.getGsmData();
                // ??? Неизвестно, от какого параметра на самом деле зависит
                // поддерживать ли долгое соединение. Возможно это fortificationState
                int batteryLevel = status.battaryLevel;
                int pedometer = inObj.getStepsCount();

                EntityManager em = getEntityManager();
                em.getTransaction().begin();
                Long ress = 0L;
                try {
                    ress = (Long)em.createQuery("SELECT count(imei) FROM BeatHeart WHERE imei = :watchImei")
                            .setParameter("watchImei", addr.IMEI).getSingleResult();
                    System.out.println("SELA BATAREIKA ====== " + batteryLevel + "QUERY: " + ress + " IMEI ======= " + addr.IMEI);
                }catch (NoResultException e){
                    e.printStackTrace();
                }
                if(!ress.equals(0L)){
                    em.createQuery("UPDATE BeatHeart SET battery = :batteryLevel, pedometer = :pedometer WHERE imei = :bhImei")
                            .setParameter("batteryLevel", batteryLevel)
                            .setParameter("pedometer", pedometer)
                            .setParameter("bhImei", addr.IMEI).executeUpdate();
                }
                em.getTransaction().commit();
                em.clear();

                longConnect.set((status.fortificationState%2)==1);                
                ProtocolDataUnit result = ProtocolManager.buildDataUnit(addr.IMEI,"BP03");                
                return result;
            }
            case "AP07": {
                PduAP07 inObj = (PduAP07) inPDU;
                int seqNumber = inObj.getSequenceNumber();
                int totalPackets = inObj.getTotalPacketsCount();
                longConnect.set(seqNumber<totalPackets);
                
                if (seqNumber==1) { 
                    uploadedAudio = new AudioDataStore(inObj); 
                } else { 
                    if (uploadedAudio.addDevicePacket(inObj)) {
                        deviceQueue.postObjectFromDevice(uploadedAudio);                        
                    }
                }                
                
                PduBP07 result = (PduBP07) ProtocolManager.buildDataUnit(addr.IMEI,"BP07");                        
                result.setDateTime(inObj.getDateTime());
                result.setTotalPacketsCount(totalPackets);
                result.setSequenceNumber(seqNumber);
                result.setSuccess(true);
                return result;
            }
            case "AP10": {
                PduBP10 result = (PduBP10) ProtocolManager.buildDataUnit(addr.IMEI,"BP10");                            
                return result;                
            }
            case "AP49": {
               ProtocolDataUnit result = ProtocolManager.buildDataUnit(addr.IMEI,"BP49");                       
               return result;               
            }
            case "APHP": {
                ProtocolDataUnit result = ProtocolManager.buildDataUnit(addr.IMEI,"BPHP");
                PduAPHP apHP = (PduAPHP) inPDU;
                EntityManager emHP = getEntityManager();
                emHP.getTransaction().begin();
                Long ress = 0L;
                try {
                    ress = (Long) emHP.createQuery("SELECT count(imei) FROM blood WHERE imei = :bloodImei")
                            .setParameter("bloodImei", addr.IMEI).getSingleResult();
                } catch (NoResultException nre){

                }
                if(!ress.equals(0L)){
                    emHP.createQuery("UPDATE blood SET oxygen =:oxygen,sugar =:sugar WHERE imei = :bloodImei")
                            .setParameter("oxygen", apHP.getSaO2())
                            .setParameter("sugar", apHP.getBloodSugar())
                            .setParameter("bloodImei", addr.IMEI).executeUpdate();
                }
                //em.merge(location);
                emHP.getTransaction().commit();
                emHP.clear();
                return result;
            }
            case "APHT": {
                ProtocolDataUnit result = ProtocolManager.buildDataUnit(addr.IMEI,"BPHT");
                PduAPHT apHT = (PduAPHT) inPDU;
                EntityManager emHT = getEntityManager();
                emHT.getTransaction().begin();
                Long ress = 0L;
                try {
                    ress = (Long)emHT.createQuery("SELECT count(imei) FROM blood WHERE imei = :bloodImei")
                            .setParameter("bloodImei", addr.IMEI).getSingleResult();
                } catch (NoResultException nre){

                }
                if(!ress.equals(0L)){
                    emHT.createQuery("UPDATE blood SET heartrate = :heartrate, dbp = :dbp, sbp = :sbp WHERE imei = :bloodImei")
                            .setParameter("heartrate", apHT.getHeartRate())
                            .setParameter("dbp", apHT.getDiastolicPressure())
                            .setParameter("sbp", apHT.getSystolicPressure())
                            .setParameter("bloodImei", addr.IMEI).executeUpdate();
                }
                //em.merge(location);
                emHT.getTransaction().commit();
                emHT.clear();
                return result;
            }            
            default : { return null; }
        }        
    }        
    
    // Отправить пакет на часы, создавшие сокет
    // Вход: ОбъектПакета
    
    public void sendPDU(ProtocolDataUnit pdu)
    {
        String msg;
        try {
            if (pdu!=null) { 
                msg = pdu.toPacket(); 
                infoLog.info(
                    ">> remote IP: "+addr.IP+                    
                    "; IMEI: "+addr.IMEI+"; пакет: "+msg
                );                
                expectedResponse = ProtocolManager.rightResponse(pdu.getCommandID());
                if (expectedResponse!=null) {
                    longConnect.set(true);
                    wrStartTime = System.currentTimeMillis();
                }                
                
                outStream.write(msg.getBytes());
                outStream.flush();                
            }            
        } catch (IOException e) {
            infoLog.debug("Ошибка отправки пакета: "+e.toString());            
        }
    }
    
    // Принять пакет от часов, создавших сокет
    // Выход: ОбъектПакета или null, если входящий поток данных отсутствует
    
    private ProtocolDataUnit receivePDU()
    {
        int r; String msg;
        byte buf[] = new byte[64*1024];
        
        try {            
            r = inStream.read(buf);
            if (r>0) { 
                msg = new String(buf,0,r);
                if (ProtocolManager.IsH10Packet(msg)) { 
                    infoLog.info(
                        "<< remote IP: "+addr.IP+                        
                        "; IMEI: "+addr.IMEI+"; пакет: "+msg
                    );                    
                    return ProtocolManager.getDataUnit(msg);
                }
            }            
        } catch (IOException e) {
            infoLog.debug("Ошибка получения пакета: "+e.toString());            
        }
        return null;
    }
    
    // Закрыть сокет
    
    private void disconnect()
    {
        try { 
            outStream.close();
            inStream.close();
            s.close();
            infoLog.info("Сокет закрыт.");            
        } catch (IOException e ) {
            infoLog.debug("Ошибка закрытия соединения: "+e.toString());            
        }
    }        
    
    // Рабочий метод потока. Параллельно выполняет две задачи:
    // 1. Принимает входящие пакеты и отвечает на них в соответствии с протоколом
    // 2. Выбирает пакеты из списка на отправку в соответствии с IMEI и отправляет их
    
    @Override
    public void run() {
    
        ProtocolDataUnit inPDU, outPDU;
                
        long keepMaxTime = NodeSettings.KEEP_ALIVE_TIMEOUT*1000;
        long respMaxTime = NodeSettings.RESPONSE_TIMEOUT*1000;
        long keepCurTime; long respCurTime; boolean l;       
        
        Runnable taskExtractPduFromMasterAndSend = () -> {
            
            ArrayList<ProtocolDataUnit> masterPduList;
            
            while (extractorActive.get()) {
            
                masterPduList = masterList.getPduListToDevice(addr.IMEI);
                if (masterPduList!=null) {
                    for (ProtocolDataUnit outPDU2 : masterPduList) { 
                        sendPDU(outPDU2);
                        infoLog.info("Пакет "+outPDU2.toPacket()+" из списка исходящих отправлен");                    
                    }
                }
                try { Thread.sleep(1000); } catch (InterruptedException e) { break; }
            }
        };
        
        Thread outListExtractor = new Thread(taskExtractPduFromMasterAndSend);
        outListExtractor.setName(getName()+"->pdu-extractor");
        outListExtractor.start();
                
        do {
            inPDU = receivePDU();
            outPDU = DispatchPacket(inPDU);
            sendPDU(outPDU);
                        
            try { sleep(60); } catch (InterruptedException e) { break; }
            keepCurTime = System.currentTimeMillis()-kaStartTime;
            respCurTime = System.currentTimeMillis()-wrStartTime;
            
            l = !isInterrupted() && (longConnect.get() | keepCurTime<keepMaxTime);
            longConnect.set(l);
            
            if ((expectedResponse!=null)&&(respCurTime>respMaxTime)) {
                infoLog.warn("Устройство не отвечает на пакет: "+expectedResponse);
                expectedResponse = null;
            }            
            
        } while (longConnect.get());
        
        extractorActive.set(false);
        try { outListExtractor.join(); } catch (InterruptedException e) {} finally { 
            disconnect(); 
        }                
    }
    
    public boolean isMyIMEI(String aIMEI)
    {
        return addr.IMEI.equals(aIMEI);
    }           
}    
