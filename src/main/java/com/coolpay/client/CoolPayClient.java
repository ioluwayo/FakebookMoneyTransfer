/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coolpay.client;

import com.coolpay.client.exception.CoolPayClientException;
import com.coolpay.client.exception.NoSuchPaymentException;
import com.coolpay.client.exception.RecipientExistsException;
import com.coolpay.client.model.Login;
import com.coolpay.client.model.Payment;
import com.coolpay.client.model.Payments;
import com.coolpay.client.model.RecipientWrapper;
import com.coolpay.client.model.Recipient;
import com.coolpay.client.model.Recipients;
import com.coolpay.client.model.Token;
import com.coolpay.client.model.PaymentWrapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import javax.ws.rs.core.MediaType;

/**
 * This class encapsulates the details for making requests to the coolpay api.
 * @author ioluwayo
 */
public class CoolPayClient {
    /**
     * Class attributes
     */
    private static final String API_ENDPOINT = "https://coolpay.herokuapp.com/api/";
    private static final String API_LOGIN_ENDPOINT = API_ENDPOINT+"login";
    private static final String RECIPIENT_ENDPOINT = API_ENDPOINT+"recipients";
    private static final String PAYMENT_ENDPOINT = API_ENDPOINT+"payments";
    /**
     * Instance attributes
     */
    private final Login login;
    private final Token token;
    private final ClientConfig clientConfig;
    private final Client client;
    /**
     * 
     * @param login
     * @throws CoolPayClientException 
     */    
    public CoolPayClient(Login login)throws CoolPayClientException{
        this.login=login;
        this.clientConfig = new DefaultClientConfig();
        this.clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        this.client = Client.create(clientConfig);
        this.token = authenticate(); // authenticate login credentials
    }
    
    // setters are not neccessary as attributes are final and should not be modified.
    public Token getToken() {
        return token;
    }

    public Login getLogin() {
        return login;
    }

    public ClientConfig getClientConfig() {
        return clientConfig;
    }

    public Client getClient() {
        return client;
    }
    /**
     * 
     * @return Token object if authentication is successful
     * @throws CoolPayClientException 
     */
    private Token authenticate()throws CoolPayClientException {
        WebResource webResource = this.client.resource(API_LOGIN_ENDPOINT);
        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, login);
        if (response.getStatus()!= 200){
            throw new CoolPayClientException(response);
        }
        return (response.getEntity(Token.class));
    }
    /**
     * This method accepts as input a recipient to be registered. It checks if it doesn't already exists before 
     * registering it. The web service has no problem registering recipients with the same name. However, it assigns different 
     * recipient ID's to them.
     * @param recipientWrapper
     * @return RecipientWrapper containing the registered recipient.
     * @throws CoolPayClientException
     * @throws RecipientExistsException 
     */
    public RecipientWrapper createRecipient(RecipientWrapper recipientWrapper) throws CoolPayClientException, RecipientExistsException{
        Recipient recipient = recipientWrapper.getRecipient();
        if(this.recipientExists(recipient)){
            throw new RecipientExistsException(recipient.getName());
        }
        WebResource.Builder webResourceBuilder = this.client.resource(RECIPIENT_ENDPOINT).header("Authorization", this.token.getTokenWithBearer());
        ClientResponse response = webResourceBuilder.type(MediaType.APPLICATION_JSON).post(ClientResponse.class,recipientWrapper);
        if (response.getStatus() != 201){
            throw new CoolPayClientException(response);
        }
        return response.getEntity(RecipientWrapper.class);
    }
    /**
     * 
     * @param paymentWrapper
     * @return PaymentWrapper containing the created payment
     * @throws CoolPayClientException 
     */
    public PaymentWrapper createPayment(PaymentWrapper paymentWrapper) throws CoolPayClientException{
        WebResource.Builder webResourceBuilder = this.client.resource(PAYMENT_ENDPOINT).header("Authorization", this.token.getTokenWithBearer());
        ClientResponse response = webResourceBuilder.type(MediaType.APPLICATION_JSON).post(ClientResponse.class,paymentWrapper);
        if (response.getStatus() != 201){ //request hasn't been fufilled.
            throw new CoolPayClientException(response);
        }
        return response.getEntity(PaymentWrapper.class);
    }
    /**
     * 
     * @return All registered recipients
     * @throws CoolPayClientException
     */
    public Recipients getAllRecipients() throws CoolPayClientException{
        WebResource.Builder webResourceBuilder = this.client.resource(RECIPIENT_ENDPOINT).header("Authorization",this.token.getTokenWithBearer());
        ClientResponse response = webResourceBuilder.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        
        if(response.getStatus() != 200){//request hasn't been fufilled
            throw new CoolPayClientException(response);
        }
        Recipients recipients = response.getEntity(Recipients.class);
        return recipients;
    }
    /**
     * 
     * @return All created payments.
     * @throws CoolPayClientException
     */
    public Payments getAllPayments() throws CoolPayClientException{
        WebResource.Builder webResourceBuilder = this.client.resource(PAYMENT_ENDPOINT).header("Authorization",this.token.getTokenWithBearer());
        ClientResponse response = webResourceBuilder.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        
        if(response.getStatus() != 200){
            throw new CoolPayClientException(response);
        }
        Payments payments = response.getEntity(Payments.class);
        return payments;
    }
    /**
     * 
     * @param paymentId
     * @return The status of the payment with the same ID as the provided ID.
     * @throws NoSuchPaymentException 
     * @throws CoolPayClientException 
     */
    public String checkStatusOfPayment(String paymentId) throws NoSuchPaymentException, CoolPayClientException{
        Payments payments = getAllPayments();
        for (Payment payment: payments.getPayments()){
            if(paymentId.equals(payment.getId())){
                return payment.getStatus();
            }
        }
        throw new NoSuchPaymentException((paymentId));
    }
    /**
     * Given a recipient, this private method checks if a recipient with the same name already exists.
     * @param recipient
     * @return True if a recipient with the same name already exists. False otherwise
     * @throws CoolPayClientException
     */
    private Boolean recipientExists(Recipient recipient) throws CoolPayClientException{
        WebResource.Builder webResourceBuilder = this.client.resource(RECIPIENT_ENDPOINT).queryParam("name", recipient.getName()).header("Authorization",this.token.getTokenWithBearer());
        ClientResponse response = webResourceBuilder.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        if(response.getStatus() != 200){
            throw new CoolPayClientException(response);
        }
        Recipients recipients = response.getEntity(Recipients.class);
        return recipients.getRecipients().length>0;
    }
    
    @Override
    public String toString() {
        return "CoolPayClient{" + "credentials=" + login + ", token=" + token + ", clientConfig=" + clientConfig + ", client=" + client + '}';
    }
    
    
}
