/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import com.coolpay.client.model.Payment;
import com.coolpay.client.model.Payments;
import junit.framework.TestCase;
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
public class PaymentsTest extends TestCase {
    private Payments payments;
    public PaymentsTest() {
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
        payments = new Payments();
    }
    
    @After
    @Override
    public void tearDown() {
        
    }

    @Test
    public void testAccessorMethods() {
        Payment[] listOfPayments = {new Payment(),new Payment()};
        payments.setPayments(listOfPayments);
        assertArrayEquals(payments.getPayments(), listOfPayments);
    }
}
