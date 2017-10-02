/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coolpay.client.application;

import com.coolpay.client.CoolPayClient;
import com.coolpay.client.exception.CoolPayClientException;
import com.coolpay.client.model.Login;
import com.sun.jersey.api.client.ClientHandlerException;

/**
 *
 * @author ioluwayo
 */
public class App {
    public static void main(String[]args){
        String username, apikey;
        // username and apikey can be passed as arguments
         if(args.length>0){
             username = args[0];
             apikey = args[1];
         }else{
             //defualt credentials will be used
             username = "IbukunO";
             apikey = "BF3B477DAE97B128";
         }
         Login login = new Login(username,apikey);
         
         try {
                // insantiate just one CoolPayClient instance for this application
                CoolPayClient coolPayClient = new CoolPayClient(login);
                ApplicationManager applicationManager = new ApplicationManager(coolPayClient);
                applicationManager.runApplication(); // start application
            }catch (CoolPayClientException e) {
                System.out.print(e.getMessage());
            }catch(ClientHandlerException ex){
                // internet connection failure...
                System.out.print(ex.getMessage());
            }
    }
}
