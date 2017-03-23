package org.se.lab;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.se.lab.business.ServiceFactory;
import org.se.lab.business.UserService;
import org.se.lab.data.User;

public class UserServiceTest 
{	
	private static final JdbcTestHelper JDBC_HELPER = new JdbcTestHelper();
	private Connection connection = null;

	private UserService service;
	
	@Before
	public void setup() throws ClassNotFoundException, SQLException  
	{		
		JDBC_HELPER.executeSqlScript("sql/createUserTable.sql");				
		connection = JDBC_HELPER.getConnection();
		
		ServiceFactory factory = new ServiceFactory();		
		service = factory.createUserService(connection); 
		
		service.addUser("Homer", "Simpson", "homer", "*********");
		service.addUser("Bart", "Simpson", "bart", "*********");
		service.addUser("Lisa", "Simpson", "lisa", "*********");
	}
	
	@After
	public void teardown() throws SQLException
	{		        
		JDBC_HELPER.executeSqlScript("sql/dropUserTable.sql");
		connection.close();
	}

	
	@Test
    public void testFindAll() 
	{			
		List<User> users = service.findAllUsers();
    
		Assert.assertEquals(3, users.size());
		
		final String EXPECTED = "[" +
				"1,Homer,Simpson,homer,b8020e8e15c5362a7ac49800e3e86e99, " +
				"2,Bart,Simpson,bart,b8020e8e15c5362a7ac49800e3e86e99, " +
				"3,Lisa,Simpson,lisa,b8020e8e15c5362a7ac49800e3e86e99" +
				"]";
		Assert.assertEquals(EXPECTED, users.toString());
    }
}
