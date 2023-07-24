package org.se.lab;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;

public abstract class AbstractTest
{
	protected final static JpaTestHelper JPA_HELPER = new JpaTestHelper();
	protected EntityManager em = JPA_HELPER.getEntityManagerForPersistenceUnit("test");
	
	protected List<Integer> ids = new ArrayList<Integer>();      
	protected Date now = new Date();
    
    @Before
    public void setUp()
    {       
    	JPA_HELPER.begin();
        
        Customer homer = new Customer("Homer", "Simpson");

        Address addr = new Address("1st Street", "Springfield", "USA");
        homer.setAddress(addr);
                        
        CreditCard card = new CreditCard("0815");
        homer.setCreditCard(card);
        card.setCustomer(homer);
        
        Phone p1 = new Phone("0316 55555");
        Phone p2 = new Phone("0676 55555 888");
        homer.getPhoneNumbers().add(p1);
        homer.getPhoneNumbers().add(p2);
                
        Reservation r1 = new Reservation(now, 20000);
        r1.getCustomers().add(homer);
        homer.getReservations().add(r1);
        
        Reservation r2 = new Reservation(now, 40000);
        r2.getCustomers().add(homer);
        homer.getReservations().add(r2);
        
        em.persist(homer);
        em.persist(r1);
        em.persist(r2);
        
        ids.add(homer.getId());
        ids.add(card.getId());

        em.flush();
        em.clear();        
    }   

    @After
    public void tearDown()
    {
    	JPA_HELPER.rollback();
    }
}
