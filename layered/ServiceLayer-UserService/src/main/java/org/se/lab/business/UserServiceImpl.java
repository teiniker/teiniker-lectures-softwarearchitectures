package org.se.lab.business;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;
import org.se.lab.data.DAOException;
import org.se.lab.data.User;
import org.se.lab.data.UserDAO;


class UserServiceImpl // package private
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
			throw new IllegalArgumentException();
		
		this.userDAO = userDAO;
	}

	
	/*
	 * Business methods
	 */
	
    public void addUser(final String firstName, final String lastName, 
			final String username, final String password) 
	{
		logger.debug("addUser(" + firstName + "," + lastName + "," + username + ")");
		
		String md5Password;
		try
		{
			md5Password = MD5Encoder.convertToMD5String(password);

			txBegin();
			getUserDAO().createUser(firstName,lastName, username, md5Password);
			txCommit();			
		}
		catch(DAOException e)
		{
			txRollback();
			logger.error(e); // Log stack trace instead of passing it to the presentation
			throw new ServiceException("Can't add user " + username);
		}
		catch(Throwable e)
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
        catch(DAOException e)
        {
            txRollback();
            logger.error(e); // Log stack trace instead of passing it to the presentation
            throw new ServiceException("Can't remove user with id = " + idString);
        }
		catch(Throwable e)
		{
			txRollback();
			logger.error(e); // Log stack trace instead of passing it to the presentation
			throw new ServiceException("Unknown error during removing: " + idString);			
		}
	}


    public List<User> findAllUsers()
	{
		logger.debug("findAllUsers()");
		
		List<User> users = null;
		try
		{
			txBegin();
			users = getUserDAO().findAll();
			txCommit();
		}
        catch(DAOException e)
        {
            txRollback();
            logger.error(e); // Log stack trace instead of passing it to the presentation
            throw new ServiceException("Can't find all user ");
        }
		catch(Throwable e)
		{
			txRollback();
			logger.error(e); // Log stack trace instead of passing it to the presentation
			throw new ServiceException("Unknown error during finding all users: ");			
		}
		return users;
	}
}
