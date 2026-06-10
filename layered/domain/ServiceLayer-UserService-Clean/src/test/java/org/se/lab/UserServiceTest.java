package org.se.lab;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.se.lab.business.ServiceFactory;
import org.se.lab.business.UserDTO;
import org.se.lab.business.UserService;

public class UserServiceTest
{	
	private static final JdbcTestHelper JDBC_HELPER = new JdbcTestHelper("src/test/resources/jdbc.properties");
	private Connection connection = null;

	private UserService service;
	
	@Before
	public void setup()
	{		
		JDBC_HELPER.executeSqlScript("src/test/resources/sql/setup.sql");
		connection = JDBC_HELPER.getConnection();
		
		ServiceFactory factory = new ServiceFactory();		
		service = factory.createUserService(connection); 
		
		service.addUser("Homer", "Simpson", "homer", "hjkH&68FFG");
		service.addUser("Bart", "Simpson", "bart", "HJH76787bnbn");
		service.addUser("Lisa", "Simpson", "lisa", "87Nmnn676&");
	}
	
	@After
	public void teardown() throws SQLException
	{		        
		JDBC_HELPER.executeSqlScript("src/test/resources/sql/teardown.sql");
		connection.close();
	}

	
	@Test
    public void testFindAll() 
	{			
		List<UserDTO> users = service.findAllUsers();
    
		Assert.assertEquals(3, users.size());
		
		final String EXPECTED = "[" +
				"UserDTO[id=1, firstname=Homer, lastname=Simpson, username=homer], " +
				"UserDTO[id=2, firstname=Bart, lastname=Simpson, username=bart], " +
				"UserDTO[id=3, firstname=Lisa, lastname=Simpson, username=lisa]" +
				"]";
		Assert.assertEquals(EXPECTED, users.toString());
    }
}
