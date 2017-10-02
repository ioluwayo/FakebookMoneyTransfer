/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import com.coolpay.client.model.Payment;
import com.coolpay.client.model.PaymentWrapper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ioluwayo
 */
public class PaymentWrapperTest {
    private PaymentWrapper paymentWrapper;
    public PaymentWrapperTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        paymentWrapper = new PaymentWrapper();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testAccessorMethods() {
        Payment payment = new Payment();
        paymentWrapper.setPayment(payment);
        assertEquals(paymentWrapper.getPayment(), payment);
    }
}
