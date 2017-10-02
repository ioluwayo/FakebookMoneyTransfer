/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coolpayclient;

import com.coolpay.client.CoolPayClient;
import com.coolpay.client.exception.CoolPayClientException;
import com.coolpay.client.exception.NoSuchPaymentException;
import com.coolpay.client.exception.RecipientExistsException;
import com.coolpay.client.model.Login;
import com.coolpay.client.model.Payment;
import com.coolpay.client.model.PaymentWrapper;
import com.coolpay.client.model.Payments;
import com.coolpay.client.model.Recipient;
import com.coolpay.client.model.RecipientWrapper;
import com.coolpay.client.model.Recipients;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
/**
 * This test should ideally be run against a demo service. As it tests methods that retrieve and create resources.
 * @author ioluwayo
 */
public class CoolPayClientTest extends TestCase {

    private CoolPayClient client;
    private Login login;
    private Recipient recipient;
    private Payment payment;

    public CoolPayClientTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    @Override
    public void setUp() throws CoolPayClientException {
        this.login = new Login("IbukunO", "BF3B477DAE97B128");
        this.client = new CoolPayClient(this.login);
        this.recipient = new Recipient();
        this.payment = new Payment();
    }

    @After
    @Override
    public void tearDown() {
    }

    /**
     * Constructor should throw a CoolPayClientException when incorrect login
     * credentials are used to authenticate
     */
    @Test
    public void testConstructor() {
        String incorectUsername = "Abcdef";
        String incorrectApikey = "yekipA";
        Login incorrectLogin = new Login(incorectUsername, incorrectApikey);
        try {
            CoolPayClient coolPayClient = new CoolPayClient(incorrectLogin);
            fail("Expected CoolPayClient exception with response status: 404");
        } catch (CoolPayClientException ex) {
            //Logger.getLogger(CoolPayClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * You should not be able to create a new recipient with the same name as as
     * recipient that already exists.
     */
    @Test
    public void testCreateRecipient() {
        String name = RandomStringUtils.randomAlphanumeric(10); // a different and random name is used.
        this.recipient.setName(name);
        RecipientWrapper recipientWrapper = new RecipientWrapper();
        recipientWrapper.setRecipient(recipient);
        try {
            RecipientWrapper createRecipient = this.client.createRecipient(recipientWrapper);
        } catch (CoolPayClientException | RecipientExistsException ex) {
            Logger.getLogger(CoolPayClientTest.class.getName()).log(Level.SEVERE, null, ex);
            // no exception should be thrown if the recipient was created sucessfully
            fail("The createRecipient method in CoolPayClient failed to register recipient");
        }
        try {
            RecipientWrapper createRecipient = this.client.createRecipient(recipientWrapper);
            fail("Expected RecipientAlreadyExists exception");
        } catch (CoolPayClientException | RecipientExistsException ex) {
            //Logger.getLogger(CoolPayClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Given the ID of a recipient, an amount and an accurate currency code, Payment should be successfully
     * created.
     */
    @Test
    public void testCreatePayment() {
        this.payment.setAmount(BigDecimal.TEN);
        this.payment.setCurrency("USD");
        this.payment.setRecipient_id("2b0f7935-87b8-4ce2-a02b-5bcb8a333c23"); // id for Jack bauer
        PaymentWrapper paymentWrapper = new PaymentWrapper();
        paymentWrapper.setPayment(payment);
        try {
            PaymentWrapper createPayment = this.client.createPayment(paymentWrapper);
        } catch (CoolPayClientException ex) {
            Logger.getLogger(CoolPayClientTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("The createPayment method in CoolPayClient failed.");
        }
    }
    @Test
    public void testGetAllRecipients() {
        try {
            Recipients recipients = this.client.getAllRecipients();
        } catch (CoolPayClientException ex) {
            Logger.getLogger(CoolPayClientTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("The getAllRecipients method in CoolPayClient failed");
        }

    }
    @Test
    public void testGetAllPayments() {
        try {
            Payments payments = this.client.getAllPayments();
        } catch (CoolPayClientException ex) {
            Logger.getLogger(CoolPayClientTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("The getAllPayments method in CoolPayClient failed");
        }
        
    }
    @Test
    public void testCheckStatusOfPayment(){
        String paymentId = "6fc25a49-64f4-4553-b293-e6294dad036b";// id of a payment to Jack Bauer
        try {
            String status = this.client.checkStatusOfPayment(paymentId);
        } catch (NoSuchPaymentException | CoolPayClientException ex) {
            Logger.getLogger(CoolPayClientTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("The checkStatusOfPayment method in coolPayClient failed");
        } 
    }
}
