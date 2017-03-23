package org.se.lab;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.junit.Test;

public class IntegerCounterTest
{    
	private final Logger LOG = Logger.getLogger(IntegerCounterTest.class);
	
	
    @Test
    public void testRemoteCounter() throws NamingException
    {
    	IntegerCounter counter = lookupEJB();
    	
    	counter.increment();
    	counter.increment();
    	counter.increment();

// Note that the counter's value will be changed with every run of the test!!    	
//     	Assert.assertEquals(3, counter.getValue());
     		
     	counter.decrement();
     	counter.decrement();
     	
//     	Assert.assertEquals(1, counter.getValue());
    }
    
    
    @Test
    public void testTwoRemoteCounters() throws NamingException
    {
    	IntegerCounter counter1 = lookupEJB();
    	IntegerCounter counter2 = lookupEJB();
    	
    	counter1.increment();
    	counter1.increment();
    	counter1.increment();

//     	Assert.assertEquals(3, counter1.getValue());
     		     	     	
     	counter2.decrement();
     	counter2.decrement();
     	
//     	Assert.assertEquals(-2, counter2.getValue());
    }

    
    private IntegerCounter lookupEJB() throws NamingException
    {
    	final Hashtable<String, String> jndiProperties = new Hashtable<String, String>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        final Context context = new InitialContext(jndiProperties);
        
        final String jndiName = "ejb:" + "" 
        		+ "/" + "EJB-Singleton-IntegerCounter" 
        		+ "/" + ""
        		+ "/" + "IntegerCounterEJB" 
        		+ "!" + IntegerCounter.class.getName();
   
        LOG.info("JNDI Name = " + jndiName);
        return (IntegerCounter) context.lookup(jndiName);
    }
}
