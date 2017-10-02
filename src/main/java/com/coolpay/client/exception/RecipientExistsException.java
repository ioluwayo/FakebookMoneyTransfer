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
public class RecipientExistsException extends Exception {
    String name;
    public RecipientExistsException(String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return "Recipient  with name = "+name+" already exists!"; //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
