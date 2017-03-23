package org.se.lab.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class UserDAO
{
	/*
	 * Persistent storage simulation
	 */
	private List<User> table = new ArrayList<User>();
	
	
	/*
	 * DAO operations
	 */
	
	public void insert(User user)
	{
		if(user == null)
			throw new IllegalArgumentException();
		table.add(user);
	}
		
	public User findByUsername(String username)
	{
		if(!User.isValidUsername(username))
			throw new IllegalArgumentException("Invalid username " + username);

		for(User u : table)
		{
			if(u.getUsername().equals(username))
				return u;
		}
		return null; // no user found
	}
	
	public List<User> findAllUsers()
	{
		return Collections.unmodifiableList(table);
	}
}
