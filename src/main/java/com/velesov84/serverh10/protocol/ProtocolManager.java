/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.velesov84.serverh10.protocol;

/**
 *
 * @author Velesov84
 */
public class ProtocolManager {
    
    public static String extractCommandID(String aPacket)
    {
        String msg = aPacket.substring(2,6);
        return msg;
    }
    
    public static boolean IsH10Packet(String aPacket)
    {
        return aPacket.startsWith("IW") && aPacket.endsWith("#");
    }    
    
    // Формирует объект входящего PDU из сообщения
    
    public static ProtocolDataUnit getDataUnit(String aPacket)
    {
        String cmdId = extractCommandID(aPacket);
        switch (cmdId) {
            case "AP00": return new PduAP00(aPacket);
            case "AP01": return new PduAP01(aPacket);
            case "AP02": return new PduAP02(aPacket);
            case "AP03": return new PduAP03(aPacket);
            case "AP07": return new PduAP07(aPacket);
            case "AP10": return new PduAP10(aPacket);
            case "AP12": return new PduAP12(aPacket);
            case "AP14": return new PduAP14(aPacket);
            case "AP16": return new PduAP16(aPacket);
            case "AP17": return new PduAP17(aPacket);
            case "AP28": return new PduAP28(aPacket);
            case "AP33": return new PduAP33(aPacket);
            case "AP49": return new PduAP49(aPacket);
            case "AP84": return new PduAP84(aPacket);
            case "AP85": return new PduAP85(aPacket);
            case "AP86": return new PduAP86(aPacket);
            case "APHP": return new PduAPHP(aPacket);
            case "APHT": return new PduAPHT(aPacket);
            case "APJZ": return new PduAPJZ(aPacket);
            case "APXL": return new PduAPXL(aPacket);
            case "APXY": return new PduAPXY(aPacket);        
            default: return null;
        }                
    }
    
    // Формирует объект исходящего PDU 
    // по команде протокола (commandID) c адресом получателя (IMEI)
    
    public static ProtocolDataUnit buildDataUnit(String IMEI, String commandID)
    {
        switch (commandID) {
            case "BP00": return new PduBP00(IMEI);
            case "BP01": return new ProtocolDataUnit(IMEI,"BP01",0);
            case "BP02": return new PduBP02(IMEI,"BP02");
            case "BP03": return new ProtocolDataUnit(IMEI,"BP03",0);
            case "BP07": return new PduBP07(IMEI);
            case "BP10": return new PduBP10(IMEI);
            case "BP12": return new PduBP12(IMEI);
            case "BP14": return new PduBP14(IMEI);
            case "BP16": return new PduBP16(IMEI);
            case "BP17": return new PduBP17(IMEI);
            case "BP28": return new PduBP28(IMEI);
            case "BP33": return new PduBP33(IMEI);
            case "BP49": return new ProtocolDataUnit(IMEI,"BP49",0);
            case "BP84": return new PduBP84(IMEI);
            case "BP85": return new PduBP85(IMEI);
            case "BP86": return new PduBP86(IMEI);
            case "BPHP": return new ProtocolDataUnit(IMEI,"BPHP",0);
            case "BPHT": return new ProtocolDataUnit(IMEI,"BPHT",0);
            case "BPJZ": return new PduBPJZ(IMEI);
            case "BPXL": return new PduBPXL(IMEI);
            case "BPXY": return new PduBPXY(IMEI);
            default: return null;
        }
    }

    // Функция возвращает commandID пакета, служащего правильным
    // ответом на входящий commandID
    
    public static String rightResponse(String aCommandID)
    {
        switch (aCommandID) {
            case "BP12": return "AP12";
            case "BP14": return "AP14";
            case "BP16": return "AP16";
            case "BP17": return "AP17";
            case "BP28": return "AP28";
            case "BP33": return "AP33";
            case "BP84": return "AP84";
            case "BP85": return "AP85";
            case "BP86": return "AP86";
            case "BPJZ": return "APJZ";
            case "BPXL": return "APXL";
            case "BPXY": return "APXY";
            default: return null;
        }        
    }  
}
