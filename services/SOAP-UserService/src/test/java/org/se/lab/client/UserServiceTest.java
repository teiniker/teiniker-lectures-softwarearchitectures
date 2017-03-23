package org.se.lab.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserServiceTest
{
	private UserService service;
	
	@Before
	public void setup()
	{
		// Instantiate a generated service stub which has two constructors.
		// When using the no-argument constructor, the WSDL location and
		// service name are implicitly taken from the @WebServiceClient
		// annotation that decorates the generated class.        
		UserServiceRemoteFacadeService ws = new UserServiceRemoteFacadeService();
		
		// The generated getHelloWorldPort() method actually is a wrapper that creates
		// a dynamic proxy using the Service's getPort() method.
		service = ws.getUserServicePort();		
	}
	
	
	@Test
	public void testLogin()
	{   
     	service.addUser("teini", "xxxxxxxxxx");     

     	Assert.assertTrue(service.login("teini", "xxxxxxxxxx"));
     		
     	Assert.assertFalse(service.login("homer", "xxxxxxxxxx"));
	}
}
