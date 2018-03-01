package org.se.lab.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserDAOTest 
{	
	private static final JdbcTestHelper JDBC_HELPER = new JdbcTestHelper("src/test/resources/jdbc.properties");
	private Connection connection = null;
	private UserDAO dao;
	
	@BeforeClass
	public static void init()
	{
		JDBC_HELPER.executeSqlScript("src/test/resources/sql/createUserTable.sql");
		JDBC_HELPER.executeSqlScript("src/test/resources/sql/insertUserTable.sql");
	}
	
	@AfterClass
	public static void destroy()
	{
		JDBC_HELPER.executeSqlScript("src/test/resources/sql/dropUserTable.sql");
	}
	
	@Before
	public void setup() throws ClassNotFoundException, SQLException  
	{
		connection = JDBC_HELPER.getConnection();
		dao = new UserDAOImpl(connection);
		JDBC_HELPER.txBegin(connection);
	}
	
	@After
	public void teardown() throws SQLException
	{		      
		JDBC_HELPER.txRollback(connection);
		connection.close();
	}


	@Test
    public void testFindById() 
	{
		User user = dao.findById(2);
        
		Assert.assertEquals("Bart", user.getFirstname());
		Assert.assertEquals("Simpson", user.getLastname());
        Assert.assertEquals("bart", user.getUsername());
        Assert.assertEquals("*******", user.getPassword());
    }

	
	@Test
    public void testFindAll() 
	{
		List<User> users = dao.findAll();
        
        Assert.assertEquals(3, users.size());
        Assert.assertEquals("homer", users.get(0).getUsername());
        Assert.assertEquals("bart", users.get(1).getUsername());
        Assert.assertEquals("lisa", users.get(2).getUsername());
    }
	
	
	@Test
	public void testInsert()
	{
		dao.createUser("Marge", "Simpson", "marge", "**********");
		
		User result = dao.findById(4);
		Assert.assertEquals("marge", result.getUsername());
		
	}
}
