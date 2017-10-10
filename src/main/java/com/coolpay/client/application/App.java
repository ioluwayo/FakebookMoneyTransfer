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
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author ioluwayo
 */
public class App {
    public static void main(String[]args){
        String username = "IbukunO" , apikey = "BF3B477DAE97B128"; //defuault credentials
        
        /**
         * command line parser options set up..
         * Optional..will only be used when provided by user
         */
        Options options = new Options();
        Option usernameOption = new Option("u", "username", true, "username for coolpay client");
        usernameOption.setRequired(false);
        usernameOption.setArgName("COOLPAY USERNAME");
        options.addOption(usernameOption);
        Option apikeyOption = new Option("a", "apikey", true, "apikey for coolpay client");
        apikeyOption.setRequired(false);
        apikeyOption.setArgName("COOLPAY APIKEY");
        options.addOption(apikeyOption);
        CommandLineParser commandLineParser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();
        CommandLine commandLine;
        try {
            commandLine = commandLineParser.parse(options, args); // will throw parse exception
            if (commandLine.hasOption("apikey") && commandLine.hasOption("username")){
                //overide default values
                username = commandLine.getOptionValue("username");
                apikey = commandLine.getOptionValue("apikey");
                System.out.println(username+" "+apikey);
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            helpFormatter.printHelp("Fakebook Moneytransfer", options);
            System.exit(1);
            return;
        }
        
        // everything is supossedly good at this point...so proceed
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
