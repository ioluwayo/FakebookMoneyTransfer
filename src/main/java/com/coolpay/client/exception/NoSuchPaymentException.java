/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coolpay.client.exception;

/**
 *
 * @author ioluwayo
 */
public class NoSuchPaymentException extends Exception {
    String id;
    public NoSuchPaymentException(String id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Payment with ID ="+id+" does not exist"; //To change body of generated methods, choose Tools | Templates.
    }
    
}
