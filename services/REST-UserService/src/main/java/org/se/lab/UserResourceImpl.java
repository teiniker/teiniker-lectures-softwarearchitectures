package org.se.lab;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserResourceImpl
	implements UserResource
{
	private final Logger LOG = LoggerFactory.getLogger(UserResourceImpl.class);
	
	private List<UserDTO> users = new ArrayList<UserDTO>();
	
	private AtomicInteger sequence = new AtomicInteger(1);
	
	private int nextId()
	{
		return sequence.getAndIncrement();
	}
	
	public UserResourceImpl()
	{
		LOG.debug("UserResourceImpl created");
		
		// Setup table
		users.add(new UserDTO(nextId(), "homer", "**********"));
		users.add(new UserDTO(nextId(), "marge", "**********"));
		users.add(new UserDTO(nextId(), "bart", "**********"));
		users.add(new UserDTO(nextId(), "lisa", "**********"));
	}
	
	
	public Response insert(UserDTO user)
	{
		LOG.debug("insert: " + user);
		
		int nextId =  nextId();
		user.setId(nextId);
		users.add(user);

		return Response.created(URI.create("/users/" + nextId)).build();
	}
	
	
	public void update(int id, UserDTO user)
	{
		UserDTO u = findById(id);
		
		LOG.debug("update: " + u + " to " + user);

		u.setUsername(user.getUsername());
		u.setPassword(user.getPassword());				
	}


	public void delete(int id)
	{
		LOG.debug("delete: " + id);
		
		UserDTO user = findById(id);
		if(user != null )
		{
			users.remove(user);
		}
	}
	
	
	public List<UserDTO> findAll(int index, int size)
	{
		LOG.debug("find all Users");
		
		if(index == 0 && size == 0)
		{
			return users;
		}
		else
		{
			LOG.debug("find all User from index=" + index + " to " + index + size);
			return users.subList(index, index+size);			
		}
	}
	
	
	public UserDTO findById(int id) throws WebApplicationException
	{
		LOG.debug("find User with id=" + id);
		
		for(UserDTO user : users)
		{
			if(user.getId() == id)
			{
				return user;
			}
		}
		
		// No existing user found
		throw new WebApplicationException(Response.Status.NOT_FOUND);
	}
}
