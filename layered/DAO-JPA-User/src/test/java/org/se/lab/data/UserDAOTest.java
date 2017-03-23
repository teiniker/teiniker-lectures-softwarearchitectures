package org.se.lab.data;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserDAOTest
{
	private static final JdbcTestHelper JDBC_HELPER = new JdbcTestHelper();
	private final static JpaTestHelper JPA_HELPER = new JpaTestHelper();

	private EntityManager em = JPA_HELPER.getEntityManager("test");
    private UserDAO dao;
       
	@BeforeClass
	public static void init()
	{
		JDBC_HELPER.executeSqlScript("sql/createUserTable.sql");
	}
	
	@AfterClass
	public static void destroy()
	{
		JDBC_HELPER.executeSqlScript("sql/dropUserTable.sql");		
	}
    
	
    @Before
    public void setUp()
    {
        dao = new UserDAOImpl(em);          
        
        JPA_HELPER.txBegin();
        
        User homer = new User(1, "homer", "simpson", "homer", "**********");
        dao.insert(homer);
        
        User marge = new User(2, "marge","simpson", "marge", "**********");
        dao.insert(marge);
    }   

    @After
    public void tearDown()
    {
    	JPA_HELPER.txRollback();
    }


    @Test
    public void testFindById()
    {
        User homer = dao.findById(1);
        
        Assert.assertEquals("homer", homer.getUsername());
        Assert.assertEquals("**********", homer.getPassword());        
    }
    
	@Test
	public void testUpdate()
	{
		User homer = dao.findById(1);

		homer.setPassword("+++++");
		dao.update(homer);

		User user = dao.findById(1);
		Assert.assertEquals("homer", user.getUsername());
		Assert.assertEquals("+++++", user.getPassword());
	}

	@Test
	public void testDelete()
	{
		User homer = dao.findById(1);
		dao.delete(homer);
	    
		User user = dao.findById(1);
	    Assert.assertNull(user);
	}

    @Test
    public void testFindAll()
    {
	    List<User> users = dao.findAll();
	    
        Assert.assertEquals(2,users.size());
        Assert.assertEquals("homer", users.get(0).getUsername());
        Assert.assertEquals("marge", users.get(1).getUsername());
        for(User u : users)
        {
            System.out.println(u.getId() + ", " + u.getUsername() + ", " + u.getPassword());
        }
    }
}
