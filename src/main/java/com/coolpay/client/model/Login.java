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
public class Login {
    String username;
    String apikey;
    
    /**
     * 
     * @param username
     * @param apikey 
     */
    public Login(String username, String apikey) {
        this.username = username;
        this.apikey = apikey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }
    

    @Override
    public String toString() {
        return "{" + "username=" + username + ", apikey=" + apikey + '}';
    }
    
}
