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
public class Recipients
{
    private Recipient[] recipients;

    public Recipient[] getRecipients() {
        return recipients;
    }

    public void setRecipients(Recipient[] recipients) {
        this.recipients = recipients;
    }

    @Override
    public String toString() {
        return "Recipients { recipients=" + Arrays.toString(recipients) + "}";
    }


}
