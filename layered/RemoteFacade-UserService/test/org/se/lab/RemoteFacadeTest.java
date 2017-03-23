package org.se.lab;


import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.se.lab.stubs.UserService;
import org.se.lab.stubs.UserServiceRemoteFacade;

public class RemoteFacadeTest
{
	private UserServiceRemoteFacade facade;
	
	@Before
	public void setUp() throws Exception
	{
		// Instantiate a generated service stub which has two constructors.
		// When using the no-argument constructor, the WSDL location and
		// service name are implicitly taken from the @WebServiceClient
		// annotation that decorates the generated class.        
		UserService service = new UserService();
		
		// The generated getxxxPort() method actually is a wrapper that creates
		// a dynamic proxy using the Service's getPort() method.
		facade = service.getUserServicePort();
	}


	@Test
	public void testAddUsers()
	{
		facade.addUser("Homer", "Simpson", "homer", "superhomer");
		facade.addUser("Marge", "Simpson", "marge", "supermarge");
		facade.addUser("Bart", "Simpson", "bart", "superbart");
		facade.addUser("Lisa", "Simpson", "lisa", "superlisa");
	}
	
	
	@Test
	public void testListUsers()
	{        
        List<String> users = facade.listUsers();
        
        System.out.println(users);
	}

}
