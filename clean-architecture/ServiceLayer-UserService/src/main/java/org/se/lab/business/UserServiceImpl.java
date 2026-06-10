package org.se.lab.business;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


public class UserServiceImpl
	extends AbstractService 
	implements UserService
{
	private final Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	/*
	 * Constructor injection
	 */
	
	public UserServiceImpl(Connection connection)
	{
		logger.debug("UserServiceImpl(" + connection + ")");
		if(connection == null)
		    throw new IllegalArgumentException("Invalid connection (null)!");

		setConnection(connection);
	}
	
	
	/*
	 * Dependency: ---[1]-> UserDAO 
	 */
	private UserDAO userDAO;
	protected final UserDAO getUserDAO()
	{
		logger.debug("getUserDAO()");
		return userDAO;
	}
	public final void setUserDAO(final UserDAO userDAO)
	{
		logger.debug("setUserDAO(" + userDAO + ")");
		
		if(userDAO == null)
			throw new IllegalArgumentException("Invalid UserDAO (null)!");
		
		this.userDAO = userDAO;
	}

	
	/*
	 * Business methods
	 */
	
    public void addUser(final String firstName, final String lastName, 
			final String username, final String password) 
	{
		logger.debug("addUser(" + firstName + "," + lastName + "," + username + ")");

		// Input Validation

		try
		{
			txBegin();
				getUserDAO().createUser(firstName, lastName, username, password);
			txCommit();
		}
		catch(RepositoryException e)
		{
			txRollback();
			logger.error(e); // Log stack trace instead of passing it to the presentation
			throw new ServiceException("Can't add user " + username);
		}
		//...
		catch(Exception e)
		{
			txRollback();
			logger.error(e); // Log stack trace instead of passing it to the presentation
			throw new ServiceException("Unknown error during adding: " + username);			
		}
	}
	

    public void removeUser(final String idString) 
	{
		logger.debug("removeUser(" + idString + ")");
		
		if(idString == null)
			throw new IllegalArgumentException();

		try
		{
			int id = Integer.valueOf(idString);
			
			txBegin();
				User u = getUserDAO().findById(id);
				getUserDAO().delete(u);
			txCommit();
		}
        catch(RepositoryException e)
        {
            txRollback();
            logger.error(e); // Log stack trace instead of passing it to the presentation
            throw new ServiceException("Can't remove user with id = " + idString);
        }
		catch(Exception e)
		{
			txRollback();
			logger.error(e); // Log stack trace instead of passing it to the presentation
			throw new ServiceException("Unknown error during removing: " + idString);			
		}
	}


    public List<UserDTO> findAllUsers()
	{
		logger.debug("findAllUsers()");

		List<User> users = null;
		try
		{
			txBegin();
			users = getUserDAO().findAll();
			List<UserDTO> result = new ArrayList<>();
			for (User u : users)
			{
				result.add(new UserDTO(
					u.getId(),
					u.getFirstname(),
					u.getLastname(),
					u.getUsername()));
			}
			txCommit();		
			return result;
		}
		catch(RepositoryException e)
        {
            txRollback();
            logger.error(e); // Log stack trace instead of passing it to the presentation
            throw new ServiceException("Can't find all user ");
        }
		catch(Exception e)
		{
			txRollback();
			logger.error(e); // Log stack trace instead of passing it to the presentation
			throw new ServiceException("Unknown error during finding all users: ");
		}

	}
}
