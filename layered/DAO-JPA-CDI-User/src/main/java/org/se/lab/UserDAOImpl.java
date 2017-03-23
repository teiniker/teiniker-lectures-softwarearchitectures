package org.se.lab;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.log4j.Logger;


@Monitored
public class UserDAOImpl 
	implements UserDAO
{
	private final Logger LOG = Logger.getLogger(UserDAOImpl.class);
	
	@Inject
    private EntityManager em;
	    
	
    /*
     * DOA operations
     */
    
	public void insert(User user) 
	{
		LOG.info("insert(" + user + ") : em = " + em);
		em.persist(user);
	}

    public void update(User p)
    {

    }

    public void delete(long id)
    {

    }

    public User findById(long id)
    {
        return em.find(User.class, id);      
    }
    
	@SuppressWarnings("unchecked")
	public List<User> findAll()
	{
		LOG.info("findAll() : em = " + em);
		List<User> users = em.createQuery("SELECT u FROM User AS u").getResultList();
		return users;
	}
   
}
