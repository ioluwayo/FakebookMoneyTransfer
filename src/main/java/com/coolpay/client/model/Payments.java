/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coolpay.client.model;

import java.util.Arrays;

/**
 *
 * @author ioluwayo
 */
public class Payments {
    Payment[] payments;

    public Payment[] getPayments() {
        return payments;
    }

    public void setPayments(Payment[] payments) {
        this.payments = payments;
    }
    
    @Override
    public String toString() {
        return "Payments{" + "payments=" + Arrays.toString(payments) + '}';
    }
    
}
