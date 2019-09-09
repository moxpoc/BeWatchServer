/*
 * Данные навигации по GPS 
 */
package com.velesov84.serverh10.protocol.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author Velesov84
 */
public class GpsData {        
    
    // Дата-время
    public Date gpsDate;
    // Это верные данные
    // при недоступности спутника всегда false
    public boolean isValid;
    // Градус широты
    public int degLatitude;
    // Минуты широты
    public double ptLatitude;
    // Знак полушария N - северное, S - южное
    public char fromEquator;
    // Градус долготы
    public int degLongitude;
    // Минуты долготы
    public double ptLongitude;
    // Знак западной (W) или восточной (E) долготы
    public char fromGreenwich;
    // Скорость (км/ч)
    public double speedKPH;
    // Азимут (градусы)
    public double degAzimuth;
    
    public GpsData(String msg) throws ParseException
    {
        String dt;        
        int np,sp,ep,wp,c;           

        DateFormat f = new SimpleDateFormat("yyMMddHHmmss");
        isValid = msg.charAt(6)=='A';
        np = msg.indexOf("N"); sp = msg.indexOf("S");
        ep = msg.indexOf("E"); wp = msg.indexOf("W");        
        if (np>=0) { fromEquator = 'N'; }
        if (sp>=0) { fromEquator = 'S'; np = sp; }
        if (ep>=0) { fromGreenwich = 'E'; }
        if (wp>=0) { fromGreenwich = 'W'; ep = wp; }
        c = ep-np;
        dt = msg.substring(0,6)+msg.substring(ep+6,ep+12);
        gpsDate = f.parse(dt);        
        
        if ((np>6)&&(c>1)) {        
            dt = msg.substring(np-7, np);
            ptLatitude = Double.parseDouble(dt);
            dt = msg.substring(7,np-7);
            degLatitude = Integer.valueOf(dt);
            dt = msg.substring(ep-7,ep);
            ptLongitude = Double.parseDouble(dt);
            dt = msg.substring(np+1,ep-7);
            degLongitude = Integer.valueOf(dt);            
        } else {
            ptLatitude = 0.0;            
            degLatitude = 0;            
            ptLongitude = 0.0;            
            degLongitude = 0;
        }
        dt = msg.substring(ep+1,ep+6);
        speedKPH = Double.parseDouble(dt);
        dt = msg.substring(ep+12);
        degAzimuth = Double.parseDouble(dt);
    }
    
    // Получить широту только в градусах (как в google maps и yandex maps)
    
    public double getMapsLatitude()
    {
        return degLatitude+ptLatitude/60;
    }
    
    // Получить долготу только в градусах
    
    public double getMapsLongitude()
    {
        return degLongitude+ptLongitude/60;
    }
}
