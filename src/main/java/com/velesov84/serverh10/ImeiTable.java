/*
 * Таблица соответствия IP и IMEI
 * При подключении устройства (т.е. получении от него пакета AP00 ) 
 * его IP адрес и IMEI заносятся в "таблицу".
 * 
 * При каждом следующем получении AP00 (примерно 1 раз в минуту)
 * IP адрес обновляется
 * 
 * Таблица существует в единственном экземпляре. 
 * 
 */
package com.velesov84.serverh10;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Velesov84
 */
// Элемент таблицы соответствия IMEI<->IP

class ImeiTableEntry {
    public String IMEI;
    public String IP;
}

//singleton

class ImeiTable {
    
    private CopyOnWriteArrayList<ImeiTableEntry> table;
    private static ImeiTable instance;
    
    public ImeiTable()
    {
        table = new CopyOnWriteArrayList();
    }
    
    public static synchronized ImeiTable getInstance()
    {
        if (instance==null) { instance = new ImeiTable(); }
        return instance;
    }        
    
    // Найти IMEI по IP адресу
    
    public synchronized String getImeiByIP(String ip)
    {
        String result = "";
        for (ImeiTableEntry e : table) {
            if (e.IP.equals(ip)) { result = e.IMEI.substring(0,15); break; }
        }        
        return result;
    }
    
    // Изменить IP по IMEI. 
    // Если IMEI еще не существует в таблице, то добавляется новая запись
    
    public synchronized void modifyIpByImei(ImeiTableEntry Value)
    {
        int i;
        
        for (i=0; i<table.size(); i++) {
            ImeiTableEntry e = table.get(i);
            if (e.IMEI.equals(Value.IMEI)) { table.set(i,Value); return; }
        }
        table.add(Value);
    }

    public synchronized ArrayList<String> getRegisteredDevices()
    {
        ArrayList<String> result = new ArrayList();

        for (ImeiTableEntry entry : table)
        {
            result.add(entry.IMEI);
        }
        return result;
    }
}
