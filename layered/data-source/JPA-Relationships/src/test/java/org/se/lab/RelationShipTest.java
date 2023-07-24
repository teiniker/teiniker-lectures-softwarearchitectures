package org.se.lab;

import org.junit.Assert;
import org.junit.Test;

public class RelationShipTest
    extends AbstractTest
{
    @Test
    public void testCustomer()
    {
        Customer homer = em.find(Customer.class, ids.get(0));
                
        Assert.assertEquals("Homer", homer.getFirstName());
        Assert.assertEquals("Simpson", homer.getLastName());
    }    

    @Test
    public void testCustomerAndAddress()
    {
        Customer homer = em.find(Customer.class, ids.get(0));
                
        Assert.assertEquals("Homer", homer.getFirstName());
        Assert.assertEquals("Simpson", homer.getLastName());
        
        Assert.assertEquals("1st Street", homer.getAddress().getStreet());
        Assert.assertEquals("Springfield", homer.getAddress().getCity());
        Assert.assertEquals("USA", homer.getAddress().getState());        
    }    

    @Test
    public void testCustomerAndAddressAndCreditCard()
    {
        Customer homer = em.find(Customer.class, ids.get(0));
                
        Assert.assertEquals("Homer", homer.getFirstName());
        Assert.assertEquals("Simpson", homer.getLastName());
        
        Assert.assertEquals("1st Street", homer.getAddress().getStreet());
        Assert.assertEquals("Springfield", homer.getAddress().getCity());
        Assert.assertEquals("USA", homer.getAddress().getState());
        
        Assert.assertEquals("0815", homer.getCreditCard().getNumber());
    }    

    @Test
    public void testCustomerAndAddressAndCreditCardAndPhone()
    {
        Customer homer = em.find(Customer.class, ids.get(0));
                
        Assert.assertEquals("Homer", homer.getFirstName());
        Assert.assertEquals("Simpson", homer.getLastName());
        
        Assert.assertEquals("1st Street", homer.getAddress().getStreet());
        Assert.assertEquals("Springfield", homer.getAddress().getCity());
        Assert.assertEquals("USA", homer.getAddress().getState());
        
        Assert.assertEquals("0815", homer.getCreditCard().getNumber());
        
        Assert.assertEquals("0316 55555", homer.getPhoneNumbers().get(0).getNumber());
        Assert.assertEquals("0676 55555 888", homer.getPhoneNumbers().get(1).getNumber());
    }    
    
    
    @Test
    public void testCreditCard()
    {
        CreditCard card = em.find(CreditCard.class, ids.get(1));
                
        Assert.assertEquals("0815", card.getNumber());
        
        Assert.assertEquals("Homer", card.getCustomer().getFirstName());
    }    
    
    
    @Test
    public void testReservations()
    {
    	Customer homer = em.find(Customer.class, ids.get(0));
    	    	
    	Assert.assertEquals(2, homer.getReservations().size());
    	
    	Reservation r1 = homer.getReservations().get(0);
    	Assert.assertEquals(20000, r1.getAmountPaid());
    
    	Reservation r2 = homer.getReservations().get(1);
    	Assert.assertEquals(40000, r2.getAmountPaid());
    }
}
