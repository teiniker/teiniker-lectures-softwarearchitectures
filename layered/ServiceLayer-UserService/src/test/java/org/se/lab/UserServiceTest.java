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
	private static final JdbcTestHelper JDBC_HELPER = new JdbcTestHelper("src/test/resources/jdbc.properties");
	private Connection connection = null;

	private UserService service;
	
	@Before
	public void setup() throws ClassNotFoundException, SQLException  
	{		
		JDBC_HELPER.executeSqlScript("src/test/resources/sql/createUserTable.sql");
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
		JDBC_HELPER.executeSqlScript("src/test/resources/sql/dropUserTable.sql");
		connection.close();
	}

	
	@Test
    public void testFindAll() 
	{			
		List<User> users = service.findAllUsers();
    
		Assert.assertEquals(3, users.size());
		
		final String EXPECTED = "[" +
				"1,Homer,Simpson,homer,BAF33CEF5D75EB329B8B6EBC326049E91191B32AB4A1D9BD3027F1EBB55F704A1BD8EFA1447793E4D365DD8CEDA8DC952B292D6EBCCD51B38FFECCAEF0FA15AD, " +
				"2,Bart,Simpson,bart,FF4FD30B9CFF88F22FF52FD728FF84354010B763C05834C695DE9EFA4FD76E227E1FCBB986C2FC84B7FC8723F823FACBEE824B2D9D5B5179481A74D8F1A64EBA, " +
				"3,Lisa,Simpson,lisa,F7DC3592BA3DBF5D1EAF3B173DCB397F295ED707BCC4123ED42C72621DD4DDF0A38E6C044B40A75ACECB999774A4D56AB6017E311873BCEB99556551FF4781C5" +
				"]";
		Assert.assertEquals(EXPECTED, users.toString());
    }
}
