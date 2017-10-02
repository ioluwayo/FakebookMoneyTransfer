/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import com.coolpay.client.model.Payment;
import java.math.BigDecimal;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author ioluwayo
 */
public class PaymentTest extends TestCase {
    private Payment payment;
    public PaymentTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    @Override
    public void setUp() {
        payment = new Payment();
    }
    
   
    @After
    @Override
    public void tearDown() {
    }
    
    @Test
    public void testAccessorMethods() {
        String currency = "GBP";
        String id = "zx-oa-29";
        String recipient_id = "90-iu-da";
        String status ="failed";
        payment.setAmount(BigDecimal.ONE);
        payment.setCurrency(currency);
        payment.setId(id);
        payment.setRecipient_id(recipient_id);
        payment.setStatus(status);
        assertEquals(payment.getAmount(), BigDecimal.ONE);
        assertEquals(payment.getCurrency(), currency);
        assertEquals(payment.getId(),id);
        assertEquals(payment.getRecipient_id(), recipient_id);
        assertEquals(payment.getStatus(), status);
    }
}
