/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import com.coolpay.client.model.Recipient;
import com.coolpay.client.model.RecipientWrapper;
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
public class RecipientWrapperTest extends TestCase {
    private RecipientWrapper recipientWrapper;
    public RecipientWrapperTest() {
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
        recipientWrapper =  new RecipientWrapper();
    }
    
    @After
    @Override
    public void tearDown() {
    }

    @Test
    public void testAccessorMethods() {
        Recipient recipient = new Recipient();
        recipientWrapper.setRecipient(recipient);
        assertEquals(recipientWrapper.getRecipient(), recipient);
    }
}
