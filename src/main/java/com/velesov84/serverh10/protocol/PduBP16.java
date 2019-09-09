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
public class PduBP16 extends PduBP0X {

    public PduBP16(String aIMEI)
    {
        super(aIMEI,"BP16",2);                
    }    
}
