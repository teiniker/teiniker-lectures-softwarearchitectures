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
				"1,Homer,Simpson,homer,baf33cef5d75eb329b8b6ebc326049e91191b32ab4a1d9bd3027f1ebb55f704a1bd8efa1447793e4d365dd8ceda8dc952b292d6ebccd51b38ffeccaef0fa15ad, " +
				"2,Bart,Simpson,bart,ff4fd30b9cff88f22ff52fd728ff84354010b763c05834c695de9efa4fd76e227e1fcbb986c2fc84b7fc8723f823facbee824b2d9d5b5179481a74d8f1a64eba, " +
				"3,Lisa,Simpson,lisa,f7dc3592ba3dbf5d1eaf3b173dcb397f295ed707bcc4123ed42c72621dd4ddf0a38e6c044b40a75acecb999774a4d56ab6017e311873bceb99556551ff4781c5" +
				"]";
		Assert.assertEquals(EXPECTED, users.toString());
    }
}
