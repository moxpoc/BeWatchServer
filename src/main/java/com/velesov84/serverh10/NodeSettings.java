// Настройки сервера

package com.velesov84.serverh10;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Velesov84
 */

class NodeSettings
{
    public static int PORT;
    public static int TIME_ZONE;
    public static String IP;
    public static int KEEP_ALIVE_TIMEOUT;
    public static int RESPONSE_TIMEOUT;
    public static int WAIT_CONNECT_TIMEOUT;
    
    private static Logger mLog = LogManager.getRootLogger();
    
    public static void loadConfig(String fileName)
    {
        try { 
            IniFile f = new IniFile(fileName);
            PORT = Integer.valueOf(f.getProperty("Address", "Port", "11011"));
            TIME_ZONE = Integer.valueOf(f.getProperty("Address", "TimeZone", "3"));
            IP = f.getProperty("Address", "Host", "127.0.0.1");
            KEEP_ALIVE_TIMEOUT = Integer.valueOf(f.getProperty("Timeouts", "KeepAlive", "180"));
            RESPONSE_TIMEOUT = Integer.valueOf(f.getProperty("Timeouts", "WaitResponse", "40"));
            WAIT_CONNECT_TIMEOUT = Integer.valueOf(f.getProperty("Timeouts", "WaitConnect", "16"));
            mLog.info("Конфигурация успешно загружена из файла: "+fileName);
        } catch (Exception e) {
            mLog.debug("Ошибка загрузки конфигурации из файла: "+e.toString());
            PORT = 11011;
            TIME_ZONE = 3;
            IP = "172.17.1.12";
            KEEP_ALIVE_TIMEOUT = 180;
            RESPONSE_TIMEOUT = 40;
            WAIT_CONNECT_TIMEOUT = 16;
        }        
    }        
}
