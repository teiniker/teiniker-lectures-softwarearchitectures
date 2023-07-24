package org.se.lab;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CustomerTest
{
	private Customer homer;
	
	@Before
	public void setup()
	{
        homer = new Customer("Homer", "Simpson");

        Address addr = new Address("1st Street", "Springfield", "USA");
        homer.setAddress(addr);
                        
        CreditCard card = new CreditCard("0815");
        homer.setCreditCard(card);
//        card.setCustomer(homer);
	}
	
	@Test
	public void testRelation()
	{
		String number = homer.getCreditCard().getNumber();
		Assert.assertEquals("0815", number);
		
		String firstName = homer.getCreditCard().getCustomer().getFirstName();
		Assert.assertEquals("Homer", firstName);
	}
}
