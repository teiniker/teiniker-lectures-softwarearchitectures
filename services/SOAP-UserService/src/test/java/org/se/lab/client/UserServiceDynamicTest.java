package org.se.lab.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class UserServiceDynamicTest
{          
	private UserService service;
	
	@Before
    public void setup() throws MalformedURLException 
    {
       URL wsdlLocation = new URL("http://localhost:8080/SOAP-UserService/UserService?wsdl");
       QName serviceName = new QName("http://server.lab.se.org/", "UserServiceRemoteFacadeService");; 
              
       // Returns a service object for the specified WSDL document and service name
       Service ws = Service.create(wsdlLocation, serviceName);        

       // Returns a proxy for the specified service endpoint interface, the Service 
       // instance is responsible for selecting the port (protocol binding and 
       // endpoint address).
       // Note that we still have to implement (or generate) the endpoint interface 
       // used by the dynamic proxy.
       service = ws.getPort(UserService.class);
    }

	
	@Test
	public void testLogin()
	{   
     	service.addUser("teini", "xxxxxxxxxx");     

     	Assert.assertTrue(service.login("teini", "xxxxxxxxxx"));
     		
     	Assert.assertFalse(service.login("homer", "xxxxxxxxxx"));
	}
}
