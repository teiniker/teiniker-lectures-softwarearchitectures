package org.se.lab.data;

import java.util.List;

import javax.persistence.EntityManager;

class UserDAOImpl
	implements UserDAO
{
	/*
	 * Constructor injection
	 */
	public UserDAOImpl(EntityManager em)
	{
		this.em = em;
	}
		
	private EntityManager em;
	public EntityManager getEntityManager()
	{
		return em;
	}
	
	
	/*
	 * CRUD methods
	 */	
	
	@Override
	public User insert(User entity)
	{
		em.persist(entity);
		return entity;
	}

	@Override
	public User update(User entity)
	{
		return em.merge(entity);
	}

	@Override
	public void delete(User entity)
	{
		em.remove(entity);
	}

	@Override
	public User findById(int id)
	{
		return em.find(User.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll()
	{
		final String hql = "SELECT u FROM " + User.class.getName() + " AS u";	    
	    return em.createQuery(hql).getResultList();
	}	
	
	
	/*
	 * Factory methods
	 */

	@Override
	public User createUser(String firstName, String lastName, String username, String password)
	{
		User u = new User();
		u.setFirstname(firstName);
		u.setLastname(lastName);
		u.setUsername(username);
		u.setPassword(password);		
		insert(u);
		return u;
	}
}
