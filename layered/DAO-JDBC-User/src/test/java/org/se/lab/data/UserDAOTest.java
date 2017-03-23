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
import org.se.lab.data.User;
import org.se.lab.data.UserDAO;
import org.se.lab.data.UserDAOImpl;

public class UserDAOTest 
{	
	private static final JdbcTestHelper JDBC_HELPER = new JdbcTestHelper();
	private Connection c = null;
	private UserDAO dao;
	
	@BeforeClass
	public static void init()
	{
		JDBC_HELPER.executeSqlScript("sql/createUserTable.sql");
		JDBC_HELPER.executeSqlScript("sql/insertUserTable.sql");
	}
	
	@AfterClass
	public static void destroy()
	{
		JDBC_HELPER.executeSqlScript("sql/dropUserTable.sql");		
	}
	
	@Before
	public void setup() throws ClassNotFoundException, SQLException  
	{
		c = JDBC_HELPER.getConnection();
		dao = new UserDAOImpl(c);		
		JDBC_HELPER.txBegin(c);		
	}
	
	@After
	public void teardown() throws SQLException
	{		      
		JDBC_HELPER.txRollback(c);
		c.close();
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
