package org.se.lab.server;


/*
 * Remoting Patterns: Remote Object
 */

public class UserServiceImpl
	implements UserService
{
	/*	
	 * Constructor 
	 */
	public UserServiceImpl()
	{
		dao = new UserDAO();
	}
	
	/*
	 * Association: ---[1]-> dao:UserDAO
	 */
	private UserDAO dao;
	
	
	/*
	 * Operations
	 */
	
	public void addUser(String username, String password)
	{
		User user = new User(username, password);
		dao.insert(user);
	}
	
	public boolean login(String username, String password)
	{
		User user = new User(username, password);
		User result = dao.findByUsername(username);
		
		if(result == null)
			return false; // unknown user
		else
		{
			if(result.getPassword().equals(user.getPassword()))
				return true; 	// right password 
			else
				return false; 	// wrong password
		}		
	}
}
