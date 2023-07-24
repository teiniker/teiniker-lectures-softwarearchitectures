package org.se.lab;

import java.util.List;
import javax.persistence.Query;
import org.junit.Assert;
import org.junit.Test;

public class QueriesTest
    extends AbstractTest
{
    @Test
    public void testSelectCustomer()
    {
        Query query = em.createQuery("SELECT c FROM Customer c");

        List<?> result = query.getResultList();
        
        Customer c = (Customer)result.get(0);
        Assert.assertEquals("Homer", c.getFirstName());
        Assert.assertEquals("Simpson", c.getLastName());        
    }    

    @Test
    public void testSelectAddress()
    {
    	// Navigate to a associated object
    	Query query = em.createQuery("SELECT c.address FROM Customer c");
        List<?> result = query.getResultList();
        
        Address addr = (Address)result.get(0); 
        Assert.assertEquals("USA", addr.getState());
        Assert.assertEquals("Springfield", addr.getCity());
        Assert.assertEquals("1st Street", addr.getStreet());
    }    

    
    @Test
    public void testSelectAddress_City()
    {
    	// Select data from an associated object
        Query query = em.createQuery(
        	"SELECT c.address.city " +
        	"FROM Customer AS c");
        List<?> result = query.getResultList();
        
        Assert.assertEquals("Springfield", result.get(0));       
    }    
    
    
    @Test
    public void testSelectCustomerWhere()
    {
        Query query = em.createQuery(
        	"SELECT c " +
        	"FROM Customer AS c " +
        	"WHERE c.firstName=:first AND c.lastName=:last");
        query.setParameter("first", "Homer");
        query.setParameter("last", "Simpson");
        List<?> result = query.getResultList();
        
        Customer c = (Customer)result.get(0);
        Assert.assertEquals("Homer", c.getFirstName());
        Assert.assertEquals("Simpson", c.getLastName());
    }    


    @Test
    public void testSelectPhoneNumbers()
    {
        Query query = em.createQuery(
        	"SELECT c.phoneNumbers " +
        	"FROM Customer AS c");
        List<?> result = query.getResultList();
        
        Phone p1 = (Phone)result.get(0);
        Phone p2 = (Phone)result.get(1);
        Assert.assertEquals("0316 55555", p1.getNumber());
        Assert.assertEquals("0676 55555 888", p2.getNumber());
    }    

    @Test
    public void testSelectNumbers()
    {
    	// The IN operator allows an identifier to represent individual 
    	// elements in a collection-based relationship field.
    	Query query = em.createQuery(
    		"SELECT n.number " +
    		"FROM Customer AS c, IN(c.phoneNumbers) n");
        List<?> result = query.getResultList();

        Assert.assertEquals("0316 55555", result.get(0));
        Assert.assertEquals("0676 55555 888", result.get(1));
    }    


    /*
     * In HQL we can specify that an associated entity instance or 
     * a collection should be eagerly fetched with the FETCH keyword
     * in the FROM clause.
     * See JPwH2007, page 650
     */
    @Test
    public void testSelectCustomerFetchJoins()
    {
    	System.out.println("testSelectCustomerFetchJoins()");
    	
    	Query query = em.createQuery(
        	"SELECT c " +
        	"FROM Customer AS c " +
        	"LEFT JOIN FETCH c.address " +
        	"LEFT JOIN FETCH c.creditCard " +
        	"LEFT JOIN FETCH c.phoneNumbers " 
        	);

/*    	
        Query query = em.createQuery(
            	"SELECT c " +
            	"FROM Customer AS c "  
            	);
*/
        List<?> result = query.getResultList();
        
        Customer c = (Customer)result.get(0);
        Assert.assertEquals("Homer", c.getFirstName());
        Assert.assertEquals("Simpson", c.getLastName());   
        
        Assert.assertEquals("Springfield", c.getAddress().getCity());
        
        Assert.assertEquals("0815", c.getCreditCard().getNumber());
        
        Assert.assertEquals("0316 55555", c.getPhoneNumbers().get(0).getNumber());
        Assert.assertEquals("0676 55555 888", c.getPhoneNumbers().get(1).getNumber());
    }    

}
