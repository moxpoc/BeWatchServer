/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.velesov84.application;

import com.bewatches.server.Model.Parent.Location;
import com.bewatches.server.Service.LocationService;
import com.velesov84.serverh10.*;
import com.velesov84.serverh10.protocol.*;
import com.velesov84.serverh10.protocol.utils.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author velesov84
 */
public class AppMain {

    private NodeH10 mNode;
    private NodeDataEventListener eventListener;
    private boolean terminate;
    private static AppMain instance;
    private int audioCounter;
    
    private String lastIMEI;
    private String journalNo;



    
    public AppMain()
    {
        lastIMEI = "358801902280093";
        journalNo = "100010";
        mNode = new NodeH10();
        eventListener = new NodeDataEventListener();
        mNode.addDataAvaivableEventListener(eventListener);
        terminate = false;
        audioCounter = 0;        
    }        
    
    // Класс-слушатель событий сервера
    
    private class NodeDataEventListener implements DataAvailableListener {
        
        public void packetDataAvailable(PacketAvailableEvent e)
        {
            ProtocolDataUnit pdu = e.getPDU();
            System.out.println("Новый пакет: "+ pdu.toPacket());
            System.out.print("?>");            
        }
        
        public void audioDataAvailable(AudioAvailableEvent e)
        {
            String fileName = "AudioData"+String.valueOf(audioCounter)+".amr";            
            AudioDataStore store = e.getAudio();
            System.out.println("Новые аудио данные. Сохраняю в файл "+fileName+"...");
            store.saveToFile(fileName);
            audioCounter++;
            System.out.print("?>");
        }        
    }    
    
    // Выполнить команду
    // список верхнего уровня (по первому слову)
    
    private void executeCommand(String cmd)
    {
        String cmdCI = cmd.toLowerCase();
        String[] params = cmdCI.split(" ");
        
        switch (params[0]) {
            // Запрос на геолокацию
            // Если часы работают в режиме emergency, то они начинают
            // переодически присылать AP01. Иначе - AP02
            case "where": {
                PduBP16 pdu = (PduBP16) ProtocolManager.buildDataUnit(lastIMEI, "BP16");                
                pdu.setJournalNo(journalNo);
                mNode.postPDU(pdu);
                break;
            }
            // Установка режима работы. см. cmdMode
            case "mode": {
                cmdMode(params);
                break;
            }
            // Проверка. см. cmdTest
            case "test": {
                cmdTest(params);
                break;
            }
            // Установка параметров. см. cmdSet
            case "set": {
                cmdSet(params);
                break;
            }
            // Сброс к заводским настройкам (Factory reset)
            case "reset": {
                PduBP17 pdu = (PduBP17) ProtocolManager.buildDataUnit(lastIMEI, "BP17");
                pdu.setJournalNo(journalNo);
                mNode.postPDU(pdu);
                break;
            }
            // Калибровка измерения давления крови
            case "calibrate-bp": {
                PduBPJZ pdu = (PduBPJZ) ProtocolManager.buildDataUnit(lastIMEI, "BPJZ");
                pdu.setJournalNo(journalNo);
                pdu.setSystolicPressure(110);
                pdu.setDiastolicPressure(75);
                mNode.postPDU(pdu);
                break;
            }
            // Загрузка аудио файла на часы
            case "audio": {
                cmdSendAudio(params);
                break;
            }
            // Выход
            case "exit": { terminate = true; }
        }        
    }
    
    // Операции команды test
    
    private void cmdTest(String[] params)
    {
        
        switch(params[1]) {
            // Измерение частоты сердцебиения
            case "heart-rate": {
                PduBPXL pdu = (PduBPXL) ProtocolManager.buildDataUnit(lastIMEI, "BPXL");                
                pdu.setJournalNo(journalNo);
                mNode.postPDU(pdu);
                break;
            }
            // Измерение давления крови
            case "blood-pressure": {
                PduBPXY pdu = (PduBPXY) ProtocolManager.buildDataUnit(lastIMEI, "BPXY");
                pdu.setJournalNo(journalNo);
                mNode.postPDU(pdu);
                break;
            }
        }                
    }
    
    // Операции команды mode
    
    private void cmdMode(String[] params)
    {
        PduBP33 pdu = (PduBP33) ProtocolManager.buildDataUnit(lastIMEI, "BP33");
        pdu.setJournalNo(journalNo);
        
        switch(params[1]) {
            // Установить нормальный режим. (Каждые 15 мин отчет о местонахождении пакетом AP02 - LBS и WIFI)
            case "normal": { pdu.setWorkingMode(PduBP33.NORMAL_MODE); break; }
            // Режим сохранения энергии. (То же, что и normal, но реже - каждые 60 мин)
            case "power-saving": { pdu.setWorkingMode(PduBP33.POWER_SAVING_MODE); break; }
            // Режим ЧС. (Каждую минуту AP01 с данными GPS, LBS и WIFI)
            case "emergency": { pdu.setWorkingMode(PduBP33.EMERGENCY_MODE); break; }
        }
        
        mNode.postPDU(pdu);
    }
    
    // Операции команды set
    
    private void cmdSet(String[] params)
    {
        switch(params[1]) {
            // Установить номера для экстренного вызова
            case "sos-phones": { 
                PduBP12 pdu = (PduBP12) ProtocolManager.buildDataUnit(lastIMEI, "BP12");
                pdu.setJournalNo(journalNo);
                pdu.setPhoneNumbers("79203670043","79621671681","");
                mNode.postPDU(pdu);
                break; 
            }
            // Установить "белый" список контактов
            case "white-list": { 
                cmdSetWhiteList(params);
                break; 
            }
            // Установить напоминания
            case "alarms": { 
                PduBP85 pdu = (PduBP85) ProtocolManager.buildDataUnit(lastIMEI, "BP85");
                pdu.setMasterSwitchForAll(false);
                ArrayList<AlarmEntry> alarms = new ArrayList();
                for (int i=0; i<3; i++) {
                    AlarmEntry alarm = new AlarmEntry("0"+String.valueOf(i+1)+"00,135,1,1");
                    alarms.add(alarm);
                }
                pdu.setAlarms(alarms);
                mNode.postPDU(pdu);
                break; 
            }
            // Установить интервал автоимерения давления или частоты сердцебиения
            case "auto-testing-timer": {
                PduBP86 pdu = (PduBP86) ProtocolManager.buildDataUnit(lastIMEI, "BP86");
                pdu.setTestingType(PduBP86.TESTING_HEART_RATE);
                pdu.setJournalNo(journalNo);
                pdu.setInterval(720);
                mNode.postPDU(pdu);
            }
        }
    }
    
    // Операции команды set white-list
    
    private void cmdSetWhiteList(String[] params)
    {
        if (params.length>2) {
            
            PduBP84 pdu = (PduBP84) ProtocolManager.buildDataUnit(lastIMEI, "BP84");
            pdu.setJournalNo(journalNo);
            
            // Вкл/Выкл "белый" лист
            
            switch (params[2]) {
                case "on": { pdu.setWhiteListEnabled(true); break; }
                case "off": { pdu.setWhiteListEnabled(false); break; }                
            }            
            mNode.postPDU(pdu);
            
        } else {
            
            PduBP14 pdu = (PduBP14) ProtocolManager.buildDataUnit(lastIMEI, "BP14");
            pdu.setJournalNo(journalNo);
            ArrayList<ContactEntry> contacts = new ArrayList();
            for (int i=0; i<10; i++) {
                String ph = "7920367004"+String.valueOf(i);
                String n = "Contact"+String.valueOf(i);
                ContactEntry ce = new ContactEntry(n,ph);
                contacts.add(ce);
            }
            pdu.setContacts(contacts);
            mNode.postPDU(pdu);            
        }
    }
    
    // команда audio ИмяФайла
    // Отправить аудио-файл на устройство
    // !!! Часы понимают только формат amr
    
    private void cmdSendAudio(String[] params)
    {
        AudioDataStore store = new AudioDataStore();
        store.loadFromFile(params[1]);
        mNode.sendAudio(lastIMEI,store,"Сервер H10","0011");
    }        
    
    
    public void execute() {
        
        Scanner sc = new Scanner(System.in);        
        String command;        
        
        System.out.println("Список команд:");
        System.out.println();
        System.out.println("-- все уточняющие настройки самих команд (пакетов) заполнены");
        System.out.println("-- случайными константами в самой программе. см. исходный код");
        System.out.println();
        System.out.println("mode {normal | power-saving | emergency}");
        System.out.println("where");
        System.out.println("set {sos-phones | white-list [on|off] | alarms | auto-testing-timer}");
        System.out.println("test {blood-pressure | heart-rate}");
        System.out.println("reset");
        System.out.println("calibrate-bp");
        System.out.println("audio {file-name}");
        System.out.println("exit");        
        System.out.println();
        
        mNode.start();
                
        System.out.println("Сервер запущен");
        
        while (!terminate) {
            System.out.print("?>");
            command = sc.nextLine();
            executeCommand(command);            
        }
        mNode.interrupt();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        instance = new AppMain();
        instance.execute();
        System.out.println("Выключаюсь...");        
    }
    
}
