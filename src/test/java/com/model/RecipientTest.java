/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import com.coolpay.client.model.Recipient;
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
public class RecipientTest extends TestCase {
    private Recipient recipient;
    public RecipientTest() {
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
        recipient = new Recipient();
        
    }
    
    @After
    @Override
    public void tearDown() {
    }

    // TODO add tests.
    @Test
    public void testAccessorMethods() {
        String name = "James";
        String ID  = "90-1s-d8";
        recipient.setName(name);
        recipient.setId(ID);
        assertEquals(recipient.getId(),ID);
        assertEquals(recipient.getName(),name);
    }
}
