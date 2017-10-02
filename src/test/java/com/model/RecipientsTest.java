/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import com.coolpay.client.model.Recipient;
import com.coolpay.client.model.Recipients;
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
public class RecipientsTest extends TestCase {
    private Recipients recipients;
    public RecipientsTest() {
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
        recipients = new Recipients();
    }
    
    @After
    @Override
    public void tearDown() {
    }

    @Test
    public void testAcessorMethods() {
        Recipient[] listOfRecipients = {new Recipient(),new Recipient()};
        recipients.setRecipients(listOfRecipients);
        assertArrayEquals(recipients.getRecipients(), listOfRecipients);
    }
}
