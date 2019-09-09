/*
 * Запись будильника (напоминания о событии) на день недели и время
 */
package com.velesov84.serverh10.protocol.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Velesov84
 */
public class AlarmEntry {
    
    public static final int REMINDER_TAKE_THE_MEDICINE = 1;
    public static final int REMINDER_DRINK_WATER = 2;
    public static final int REMINDER_SEDENTARY = 3;
    
    public Date time;
    public int[] daysOfWeek;
    public boolean enabled;
    public int reminderType;
    
    public AlarmEntry(String msg)
    {
        int i;
        DateFormat f = new SimpleDateFormat("HHmm");
        String[] p = msg.split(",");
        try { time = f.parse(p[0]); } catch (ParseException e) {
            System.out.println("Ошибка разбора времени: "+e.toString());
            time = new Date();
        }
        daysOfWeek = new int[p[1].length()];
        for (i=0; i<p[1].length(); i++) {
            daysOfWeek[i] = Integer.valueOf(p[1].substring(i,i+1));
        }
        enabled = p[2].equals("1");
        reminderType = Integer.valueOf(p[3]);        
    }
    
    @Override
    public String toString()
    {
        int i; String result;
        DateFormat f = new SimpleDateFormat("HHmm");
        result = f.format(time)+",";
        for (i=0; i<daysOfWeek.length; i++) {
            result += Integer.toString(daysOfWeek[i]);
        }
        result += ","+(enabled ? "1":"0")+","+Integer.toString(reminderType);
        return result;
    }
    
}
