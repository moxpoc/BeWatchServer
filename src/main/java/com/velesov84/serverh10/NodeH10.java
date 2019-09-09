// Управляющий (главный) поток
// Ждет подключения и передает сокет потоку-обработчику
// Ставит исходящие PDU в список на отправку

package com.velesov84.serverh10;


import java.net.*;
import java.util.LinkedList;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.velesov84.serverh10.protocol.*;
import com.velesov84.serverh10.protocol.utils.AudioDataStore;

public class NodeH10 extends Thread {
    
    private ServerSocket server;    
    private MasterPacketsList packetList;
    private LinkedList<NodeWorker> workers;
    private DataAvailableNotifier notifier;    
    private static Logger sLog = LogManager.getLogger(NodeH10.class); 
    
    public NodeH10()
    {
        workers = new LinkedList();
        notifier = DataAvailableNotifier.getInstance();        
        packetList = MasterPacketsList.getInstance();
        NodeSettings.loadConfig("node-settings.conf");
        
        try {
            server = new ServerSocket(
                NodeSettings.PORT,0,InetAddress.getByName(NodeSettings.IP)
            );
            server.setSoTimeout(NodeSettings.WAIT_CONNECT_TIMEOUT*1000);
            sLog.info("Сервер H10 запускается по адресу: "+NodeSettings.IP+":"+String.valueOf(NodeSettings.PORT));            
        } catch(Exception e) {
            sLog.debug("Ошибка при запуске сервера H10: "+e.toString()+"\nПроверьте правильность IP адреса. IP должен совпадать с одним из IP адресов Вашего хоста.");
            return;
        }
        
        setName("node-thread");
        setDaemon(false);
        setPriority(MIN_PRIORITY);
    }
    
    // Найти поток-обработчик подключения по его IMEI
    
    private NodeWorker findWorkerByIMEI(String aIMEI)
    {
        for (NodeWorker w : workers) {
            if (w.isMyIMEI(aIMEI)) { return w; }
        }
        return null;
    }        
    
    // Отправить пакет на устройство
    // Если сокет нужного IMEI активен, то пакет передается его потоку-обработчику
    // Иначе - пакет ставится в очередь на отправку
    // Вход : Объект пакета протокола
    
    public void postPDU(ProtocolDataUnit Value)
    {
        NodeWorker sender = findWorkerByIMEI(Value.getIMEI());
        
        if (sender!=null) { 
            sender.sendPDU(Value); 
        } else {
            packetList.postPduFromMaster(Value);
        }    
    }
    
    // Отправить аудио данные на устройство
    // Вход: 
    //   IMEI получачеля, Объект-хранилище аудио с загруженными данными
    //   ИмяОтправителя, ДополнительныеПараметры
    
    public void sendAudio(String receiverIMEI, AudioDataStore audio, String senderName, String additionalParams)
    {
        PduBP28[] packets = audio.getOutcomingPackets(receiverIMEI,senderName,additionalParams);
        NodeWorker sender = findWorkerByIMEI(receiverIMEI);
        int i;
        
        for (i=0; i<packets.length; i++) {
            packetList.postPduFromMaster(packets[i]);
        }        
    }        
    
    // Рабочий метод потока сервера
    // Постоянно ожидает новых подключений и запускает для них
    // потоки-обработчики. Также при старте запускает параллелный поток,
    // уведомляющий слушателей о событиях. При завершении 
    // этот поток также прерывается.
    
    @Override
    public void run()
    {
        notifier.start();
        try { 
            while(!isInterrupted()) {
                try {
                    workers.add(new NodeWorker(server.accept()));                    
                } catch(SocketTimeoutException e) {
                    //sLog.info("Время ожидания соединения истекло");            
                }
            }
        } catch(IOException e) {
            sLog.debug("Ошибка в главном потоке сервера: "+e.toString());            
        } finally { 
            notifier.interrupt();
            for (NodeWorker worker : workers) { worker.interrupt(); }
        }        
    }    

    // Получить количество активных соединений
    
    public int getConnectionsCount()
    {
        synchronized (workers) { return workers.size(); }
    }
    
    // Методы, управляющие подпиской на события
    
    public void addDataAvaivableEventListener(DataAvailableListener listener)
    { 
        notifier.addDataAvaivableEventListener(listener);
    }
    
    public DataAvailableListener[] getDataAvaivableEventListeners()
    {
        return notifier.getDataAvaivableEventListeners();
    }
    
    public void removeDataAvaivableEventListener(DataAvailableListener listener)
    {
        notifier.removeDataAvaivableEventListener(listener);
    }
}