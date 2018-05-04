package org.se.lab;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;
import org.junit.Assert;
import org.junit.Test;

public class IntegerConverterITCase
{    
	private final Logger LOG = Logger.getLogger(IntegerConverterITCase.class);
	
    
    @Test
    public void testRemoteConverter() throws NamingException
    {
    	IntegerConverter converter = lookupEJB();
    	
    	String hex = converter.toHex(0xffd2);
     	Assert.assertEquals("ffd2", hex);
     		
       	String bin = converter.toBin(0xffd2);
     	Assert.assertEquals("1111111111010010", bin);     		
    }

    @Test
    public void testRemoteConverterLoop() throws NamingException
    {
    	IntegerConverter converter = lookupEJB();
    	
    	String hex;
    	for(int i=0; i<10; i++)
    	{
    		hex = converter.toBin(0xffd2);
    		Assert.assertEquals("1111111111010010", hex);
    	}
    }
    
    /*
     * Simulate many clients via threads
     */
    @Test
    public void testRemoteConverterThreads() throws NamingException
    {
    	IntegerConverter converter = lookupEJB();
    	
    	for(int i=0; i<10; i++)
    	{
    		Thread t = new Thread(new Runnable() {
    			public void run()
    			{
    				System.out.println("Thread " + Thread.currentThread().getId());
    				String hex = converter.toBin(0xffd2);
    	    		System.out.println(hex);
    			}
    		});
    		t.start();
    	}
    }
 
    private IntegerConverter lookupEJB() throws NamingException
    {
    	final Hashtable<String, String> jndiProperties = new Hashtable<String, String>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        final Context context = new InitialContext(jndiProperties);
        
        final String jndiName = "ejb:" + "" 
        		+ "/" + "EJB-Stateless-IntegerConverter" 
        		+ "/" + ""
        		+ "/" + "IntegerConverterEJB" 
        		+ "!" + IntegerConverter.class.getName();
   
        LOG.info("JNDI Name = " + jndiName);
        IntegerConverter counter =  (IntegerConverter) context.lookup(jndiName);
        return counter;
    }
}
