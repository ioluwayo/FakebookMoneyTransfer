/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coolpay.client.exception;

import com.sun.jersey.api.client.ClientResponse;

/**
 *
 * @author ioluwayo
 */
public class CoolPayClientException extends Exception {

    public CoolPayClientException(ClientResponse response) {
        super(response.toString());
    }
    
    @Override
    public String getMessage() {
        return super.getMessage();
    }
    
}
