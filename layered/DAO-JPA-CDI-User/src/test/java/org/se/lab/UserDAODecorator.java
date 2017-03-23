package org.se.lab;

import java.util.List;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

@Decorator
public class UserDAODecorator
	implements UserDAO
{
	private final Logger LOG = Logger.getLogger(UserDAODecorator.class);
	
	@Inject 
	private EntityManager em;
	
	@Inject @Delegate
	private UserDAO dao;

	
	@Override
	public void insert(User user)
	{
		LOG.debug("insert(" + user + ")");
		dao.insert(user);		
	}

	@Override
	public void update(User user)
	{
		LOG.debug("update(" + user + ")");
		dao.update(user);
	}

	@Override
	public void delete(long id)
	{
		LOG.debug("delete(" + id + ")");
		dao.delete(id);
	}

	@Override
	public User findById(long id)
	{
		LOG.debug("findById(" + id + ")");
		User user = dao.findById(id);
		return user; 
	}

	@Override
	public List<User> findAll()
	{
		LOG.debug("findAll() : em = " + em);
		return dao.findAll();
	}
}
