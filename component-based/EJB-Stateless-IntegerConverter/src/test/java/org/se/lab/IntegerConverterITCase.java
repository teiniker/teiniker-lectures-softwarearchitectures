package org.se.lab;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;
import org.junit.Assert;
import org.junit.Test;

public class IntegerConverterITCase
    extends AbstractEJBTest
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
}
