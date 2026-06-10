package org.se.lab.business;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

abstract class AbstractService // package private
{
	private final Logger logger = Logger.getLogger(AbstractService.class);
	
	/*
	 * Dependency: ---[1]-> connection:Connection
	 */
	protected Connection connection;
	protected Connection getConnection()
	{
		return connection;
	}
	public void setConnection(Connection connection)
	{
		if(connection == null)
			throw new NullPointerException("connection");
		this.connection = connection;
	}
	
	
	
	/*
	 * Transaction methods
	 */
	
	protected void txBegin() 
	{
		try
        {
            connection.setAutoCommit(false);
        } 
		catch (SQLException e)
        {
			logger.error(e); // Log stack trace instead of passing it to the presentation
		    throw new ServiceException("transaction begin failure");
        } 
	}

	protected void txCommit() 
	{
		try
        {
            connection.commit();
            connection.setAutoCommit(true);
        } 
		catch (SQLException e)
        {
			logger.error(e); // Log stack trace instead of passing it to the presentation
		    throw new ServiceException("transaction commit failure");
        }
	}

	protected void txRollback() 
	{
		try
        {
            connection.rollback();
        } 
		catch (SQLException e)
        {
			logger.error(e); // Log stack trace instead of passing it to the presentation
            throw new ServiceException("transaction rollback failure");
        }
	}
}