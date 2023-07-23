package org.se.lab;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EntityManagerTest
{
	private final static JpaTestHelper JPA_HELPER = new JpaTestHelper();
	private EntityManager em = JPA_HELPER.getEntityManagerForPersistenceUnit("test");
	
    private List<Integer> ids = new ArrayList<>();
    
    @Before
    public void setUp()
    {
    	JPA_HELPER.begin();
        
        User homer = new User("homer", "2AAAB795B3836904F82EFC6CA2285D927AED75206214E1DA383418EB90C9052F");
        em.persist(homer);
        ids.add(homer.getId());
        
        User marge = new User("marge", "4C4269E13B33EAC0C54E38D2447B3C1283B9C6F6EE06DDC4EFCD0D4C45771302");
        em.persist(marge);
        ids.add(marge.getId());
        
        em.flush();
        em.clear();
    }   

    @After
    public void tearDown()
    {
    	JPA_HELPER.rollback();
    }


    @Test
    public void testFindById()
    {
        User homer = em.find(User.class, ids.get(0));
        
        Assert.assertEquals("homer", homer.getUsername());
        Assert.assertEquals("2AAAB795B3836904F82EFC6CA2285D927AED75206214E1DA383418EB90C9052F", homer.getPassword());        
    }
        
    @Test
    public void testFindAll()
    {
    	final String hql = "SELECT u FROM User AS u";
	    List<User> users = em.createQuery(hql, User.class).getResultList();
	    
        Assert.assertEquals(2,users.size());
        for(User u : users)
        {
            System.out.println(u.getId() + ", " + u.getUsername() + ", " + u.getPassword());
        }
    }

    
	@Test
	public void testUpdate()
	{
		User homer = em.find(User.class, ids.get(0));

		homer.setPassword("EAAC7713FF13E74D61FA40E87E3903191F9C08BCD8D87658DADB6E3E490754A1");
		
		em.flush();
		em.clear();
		
		User user = em.find(User.class, ids.get(0));
		Assert.assertEquals("homer", user.getUsername());
		Assert.assertEquals("EAAC7713FF13E74D61FA40E87E3903191F9C08BCD8D87658DADB6E3E490754A1", user.getPassword());
	}

	
	@Test
	public void testDelete()
	{
		User homer = em.find(User.class, ids.get(0));
		em.remove(homer);
		
		em.flush();
	    em.clear();
		
		User user = em.find(User.class, ids.get(0));
	    Assert.assertNull(user);
	}	
}
