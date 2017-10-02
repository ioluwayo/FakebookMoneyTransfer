/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coolpay.client.application;

import com.coolpay.client.CoolPayClient;
import com.coolpay.client.exception.CoolPayClientException;
import com.coolpay.client.exception.NoSuchPaymentException;
import com.coolpay.client.exception.RecipientExistsException;
import com.coolpay.client.model.Payment;
import com.coolpay.client.model.PaymentWrapper;
import com.coolpay.client.model.Payments;
import com.coolpay.client.model.Recipient;
import com.coolpay.client.model.RecipientWrapper;
import com.coolpay.client.model.Recipients;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Formatter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ioluwayo
 */
public class ApplicationManager {

    private final Scanner inputScanner; //to handle all user input
    private final CoolPayClient coolPayClient; // hides away http request details.
    //ideally only one instance of this coolPayClient should be created.
    
    /**
     *
     * @param coolPayClient
     */
    public ApplicationManager(CoolPayClient coolPayClient) {
        this.coolPayClient = coolPayClient;
        this.inputScanner = new Scanner(System.in);
    }

    /**
     * Method for routing users to appropriate subtasks based on their
     * selection. The loop in this method makes it possible for the user to
     * continue using the application until they purposely quit it.
     */
    public void runApplication() {
        applicationLoop:
        while (true) {
            int selection = getUserSelection();
            switch (selection) {
                case 1:
                    sendMoney();
                    break;
                case 2:
                    addRecipient();
                    break;
                case 3:
                    listAllRecipients();
                    break;
                case 4:
                    checkStatusOfPayment();
                    break;
                case 5:
                    listAllPayments();
                    break;
                case 6:
                    System.out.println("Thank you for using the Fakebook money transfer application.");
                    inputScanner.close(); // only close this resource when the user quits
                    break applicationLoop; // break out of infinite loop
            }
        }
    }

    /**
     *
     * @return an Integer representing the subtask the user wants to perform.
     */
    private int getUserSelection() {
        StringBuilder builder = new StringBuilder();
        Formatter formatter = new Formatter(builder);
        formatter.format("%s", "\n---Fakebook money transfer menu---\n");
        formatter.format("%-20s %s", "SELECTION", "DESCRIPTION\n");
        formatter.format("%-20s %s", "1:", "Send money.\n");
        formatter.format("%-20s %s", "2:", "Add a new recipient.\n");
        formatter.format("%-20s %s", "3:", "List all recipients.\n");
        formatter.format("%-20s %s", "4:", "Check the status of a payment using the payment ID.\n");
        formatter.format("%-20s %s", "5:", "List all payments.\n");
        formatter.format("%-20s %s", "6:", "Quit Fakebook money transfer.\n");
        formatter.format("Enter selection from 1-6: ");
        System.out.print(builder);
        formatter.close();
        try {
            int intValue = Integer.parseInt(inputScanner.nextLine());
            if (intValue < 1 || intValue > 6) {
                System.out.println("Input is outside the valid range. Try again\n");
                return getUserSelection(); //recur until the input is valid
            }
            return intValue;
        } catch (NumberFormatException ex) {
            System.out.println("Input is not a valid integer. Try again\n");
            return getUserSelection(); // recur untill the input is valid
        }
    }

    /**
     * This method asks the user to select a recipient, currency and amount of
     * money the user intends to pay the recipient.
     */
    private void sendMoney() {
        try {
            System.out.println("---SEND MONEY---");
            Recipients recipients = this.coolPayClient.getAllRecipients(); // get all the registerd recipients..throws exception
            Recipient recipient = this.getUserChoiceFromListOfAllRecipients(recipients); //uses this method to get the user's recipient of choice
            System.out.println("You are sending money to " + recipient.getName());
            Payment payment = new Payment();
            String currencyCode = this.getPaymentCurrency();
            BigDecimal amount = this.getPaymentAmountFromUser();
            payment.setAmount(amount);
            payment.setCurrency(currencyCode);
            payment.setRecipient_id(recipient.getId());
            PaymentWrapper unregisteredPaymentWrapper = new PaymentWrapper();
            unregisteredPaymentWrapper.setPayment(payment);
            //the wrapper object is necessary due to the structure of the REST webservice.
            // it serializes to exaclty what the webservice expects.
            PaymentWrapper createdPaymentWrapper = this.coolPayClient.createPayment(unregisteredPaymentWrapper); // throws exception
            Payment createdPayment = createdPaymentWrapper.getPayment();
            System.out.println("Payment created! ID = " + createdPayment.getId());
        } catch (CoolPayClientException ex) {
            Logger.getLogger(ApplicationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates and registers a new recipient if a recipient with 
     * the entered name doesn't exist already.
     */
    private void addRecipient() {
        System.out.println("---ADD A RECIPIENT---");
        System.out.print("Enter the name of the new recipient: ");
        String name = inputScanner.nextLine();
        Recipient recipient = new Recipient();
        recipient.setName(name);
        RecipientWrapper recipientWrapper = new RecipientWrapper();
        recipientWrapper.setRecipient(recipient);
        try {
            coolPayClient.createRecipient(recipientWrapper);
            System.out.println("Recipient created! You can now send money to them.");
        } catch (CoolPayClientException ex) {
            System.out.println(ex.getMessage());
        } catch (RecipientExistsException ex) {
            // only if a recipient with the given name already exists..
            System.out.println("A Recipient with name = "+name+ " already exists");
            //Logger.getLogger(ApplicationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Retrieves and displays all the registered recipients.
     */
    private void listAllRecipients() {
        System.out.println("---LIST ALL RECIPIENTS---");
        try {
            Recipients recipients = coolPayClient.getAllRecipients(); //throws exception if unsucessfull
            
            StringBuilder builder = new StringBuilder();
            Formatter formatter = new Formatter(builder);
            formatter.format("%-10s %-20s %s\n", "No", "Name", "ID");
            int count = 0; // to count the number of recipients
            for (Recipient recipient : recipients.getRecipients()) {
                count++;
                String name = recipient.getName();
                String id = recipient.getId();
                formatter.format("%-10d %-20s %s\n", count, name, id);
            }
            System.out.print(builder); // print after iterating through entire list
            formatter.close();
            
        } catch (CoolPayClientException ex) {
            System.out.println(ex.getMessage());
            //Logger.getLogger(ApplicationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Use the id of a payment to check its status.
     */
    private void checkStatusOfPayment() {
        System.out.println("---CHECK STATUS OF PAYMENT---");
        System.out.print("Enter the ID of the payment: ");
        String paymentId = inputScanner.nextLine();
        try {
            String status = coolPayClient.checkStatusOfPayment(paymentId);
            System.out.println("Status: " + status);
        } catch (NoSuchPaymentException ex) {
            //The provided id doesn't match any payment.
            System.out.println("There is no payment with ID = "+paymentId);
        } catch (CoolPayClientException ex) {
            System.out.println(ex.getMessage());
            //Logger.getLogger(ApplicationManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /**
     * Get and display all created payments.
     */
    private void listAllPayments() {
        try {
            System.out.println("---LIST ALL PAYMENTS---");
            Payments payments = coolPayClient.getAllPayments();
            StringBuilder builder = new StringBuilder();
            Formatter formatter = new Formatter(builder);
            formatter.format("%-4s %-10s %-10s %-50s %-5s", "No", "Amount", "Currency", "Payment ID", "Status\n");
            int count = 0; // to count the number of payments
            for (Payment payment : payments.getPayments()) {
                count++;
                BigDecimal amount = payment.getAmount();
                String currency = payment.getCurrency();
                String id = payment.getId();
                String status = payment.getStatus();
                formatter.format("%-4s %-10.2f %-10s %-50s %-5s\n", count, amount, currency, id, status);
            }
            System.out.print(builder);// now display entire payload..
            formatter.close();
        } catch (CoolPayClientException ex) {
            System.out.println(ex.getMessage());
            //Logger.getLogger(ApplicationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @return The payment amount entered by the user.
     * BigDecimal provides the precision required for handling money.
     */
    private BigDecimal getPaymentAmountFromUser() {
        System.out.print("Enter the amount you want to send to the recipient: ");
        try {
            BigDecimal amount = new BigDecimal(inputScanner.nextLine());
            return amount;
        } catch (NumberFormatException ex) {
            System.out.println("Input is not a valid amount. Try again\n");
            return getPaymentAmountFromUser();
        }
    }
    
    /**
     * 
     * @return The currency code entered by the user. Uses Java.util.Currency class
     * to validate user input.
     */
    private String getPaymentCurrency() {
        System.out.print("Enter the currency. e.g 'GBP' for British pounds: ");
        try {
            String currencyCode = inputScanner.nextLine();
            Currency.getInstance(currencyCode); //valid code will not throw exception
            return currencyCode;
        } catch (IllegalArgumentException ex) {
            System.out.println("Input is not a valid currency code. Try again\n");
            return getPaymentCurrency();
        }
    }
    
    /**
     * 
     * @param Recipient recipients
     * @return The Recipient selected by the user.
     */
    private Recipient getUserChoiceFromListOfAllRecipients(Recipients recipients) {
        System.out.println("Please choose one of the following recipients.");
        Recipient[] listOfAllRecipients = recipients.getRecipients();
        StringBuilder builder = new StringBuilder();
        Formatter formatter = new Formatter(builder);
        formatter.format("%-10s %-20s %20s\n", "No", "Name", "Id");
        int count = 0; // to count the number of recipients
        for (Recipient recipient : listOfAllRecipients) {
            count++;
            String name = recipient.getName();
            String id = recipient.getId();
            formatter.format("%-10d %-20s %20s\n", count, name, id);
        }
        System.out.print(builder);
        formatter.close();
        System.out.print("Enter a selection from 1-" + count + ": ");
        try {
            int intValue = Integer.parseInt(inputScanner.nextLine());
            if (intValue < 1 || intValue > count) {
                System.out.println("Input is outside the valid range. Try again\n");
                inputScanner.close();
                return getUserChoiceFromListOfAllRecipients(recipients); // recur until user provides a valid input
            }
            return listOfAllRecipients[intValue - 1];
        } catch (NumberFormatException ex) {
            System.out.println("Input is not a valid integer. Try again\n");
            return getUserChoiceFromListOfAllRecipients(recipients); //  try again until user gets is right
        }
    }
}
