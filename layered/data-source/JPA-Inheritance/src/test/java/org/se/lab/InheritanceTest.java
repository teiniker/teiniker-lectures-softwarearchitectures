package org.se.lab;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InheritanceTest
{
	private final static JpaTestHelper JPA_HELPER = new JpaTestHelper();
	private EntityManager em = JPA_HELPER.getEntityManagerForPersistenceUnit("test");
    private List<Integer> ids = new ArrayList<Integer>();
    
    @Before
    public void setUp()
    {
    	JPA_HELPER.begin();
        
        Person bart = new Person("Bart", "Simpson");
        em.persist(bart);
        ids.add(bart.getId());
        
        Person marge = new Customer("Marge", "Simpson", "1st Streat", "Springfield", "USA");
        em.persist(marge);
        ids.add(marge.getId());
        
        Person homer = new Employee("Homer", "Simpson", 666);
        em.persist(homer);
        ids.add(homer.getId());
        
        em.flush();
        em.clear();
    }   

    @After
    public void tearDown()
    {
    	JPA_HELPER.rollback();
    }


    @Test
    public void testFindCustomer()
    {
        Customer marge = em.find(Customer.class, ids.get(1));
        
        Assert.assertEquals("Marge", marge.getFirstName());
        Assert.assertEquals("Simpson", marge.getLastName());
        Assert.assertEquals("1st Streat", marge.getStreet());
        Assert.assertEquals("Springfield", marge.getCity());
        Assert.assertEquals("USA", marge.getState());
    }
    
    
    @Test
    public void testFindEmployee()
    {
        Employee homer = em.find(Employee.class, ids.get(2));
        
        Assert.assertEquals("Homer", homer.getFirstName());
        Assert.assertEquals("Simpson", homer.getLastName());
        Assert.assertEquals(666, homer.getEmployeeId());
    }
    
    
    @Test
    public void testPerson()
    {
        Person bart = em.find(Person.class, ids.get(0));
        
        Assert.assertEquals("Bart", bart.getFirstName());
        Assert.assertEquals("Simpson", bart.getLastName());
    }    


    @Test
    public void testFindEmployeeAsPerson()
    {
        Person homer = em.find(Person.class, ids.get(2));
        
        Assert.assertEquals("Homer", homer.getFirstName());
        Assert.assertEquals("Simpson", homer.getLastName());
    }

}
