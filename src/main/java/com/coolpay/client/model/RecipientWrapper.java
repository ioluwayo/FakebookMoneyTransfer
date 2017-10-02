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
public class RecipientWrapper
{
    private Recipient recipient;

    public Recipient getRecipient ()
    {
        return recipient;
    }

    public void setRecipient (Recipient recipient)
    {
        this.recipient = recipient;
    }

    @Override
    public String toString() {
        return "RecipientWrapWrapper {" + "recipient=" + recipient + '}';
    }
    

}