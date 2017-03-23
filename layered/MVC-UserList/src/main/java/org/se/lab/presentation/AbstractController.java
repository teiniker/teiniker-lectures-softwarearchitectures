package org.se.lab.presentation;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public abstract class AbstractController
{
	private final Logger LOG = Logger.getLogger(ControllerServlet.class); 
	private final static String DATA_SOURCE_NAME = "java:jboss/datasources/MySqlDS";
	
	protected Connection createConnection()
	{
		LOG.debug("create connection from datasource: " + DATA_SOURCE_NAME);
		
		Connection c = null;
		try
		{
			Context initialContext = new InitialContext();
			DataSource ds = (DataSource)initialContext.lookup(DATA_SOURCE_NAME); 
			c = ds.getConnection();
			return c;
		}
		catch (NamingException | SQLException e)
		{
			throw new IllegalStateException("Can't create connection!", e);
		}
	}
}
