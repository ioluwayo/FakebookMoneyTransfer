/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coolpay.client.model;

/**
 *
 * @author ioluwayo
 */
public class Token {
    private String token;

    public Token() {
        
    }
    
    public String getToken() {
        return token;
    }
    /**
     * To prevent hard coding the string repeatedly
     * @return Bearer +token
     */
    public String getTokenWithBearer(){
       return "Bearer "+token; 
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Token{" + "token=" + token + '}';
    }
    
    
    
}
