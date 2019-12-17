package org.se.lab.data;

import org.jboss.logging.Logger;
import org.se.lab.service.UserServiceEJB;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
class UserDAOImpl // package private
	implements UserDAO
{
	private final Logger LOG = Logger.getLogger(UserDAOImpl.class);

	@PersistenceContext
	private EntityManager em;

	
	/*
	 * CRUD methods
	 */	
	
	@Override
	public User insert(User entity)
	{
		LOG.info("insert: " + entity);
		em.persist(entity);
		return entity;
	}

	@Override
	public User update(User entity)
	{
		LOG.info("update: " + entity);
		return em.merge(entity);
	}

	@Override
	public void delete(User entity)
	{
		LOG.info("delete: " + entity);
		em.remove(entity);
	}

	@Override
	public User findById(int id)
	{
		LOG.info("findById: " + id);
		return em.find(User.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll()
	{
		LOG.info("findAll: " + User.class.getName());
		final String hql = "SELECT u FROM " + User.class.getName() + " AS u";	    
	    return em.createQuery(hql).getResultList();
	}	
	
	
	/*
	 * Factory methods
	 */

	@Override
	public User createUser(String username, String password)
	{
		User u = new User();
		u.setUsername(username);
		u.setPassword(password);		
		insert(u);
		return u;
	}
}
