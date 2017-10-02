/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coolpay.client.model;

import java.math.BigDecimal;

/**
 *
 * @author ioluwayo
 */
public class Payment {
    private String id;
    private BigDecimal amount;
    private String currency;
    private String recipient_id;
    private String status;

    public String getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getRecipient_id() {
        return recipient_id;
    }

    public String getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setRecipient_id(String recipient_id) {
        this.recipient_id = recipient_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "id=" + id + ", amount=" + amount + ", currency=" + currency + ", recipient_id=" + recipient_id + ", status=" + status;
    }
    

    
    
}
