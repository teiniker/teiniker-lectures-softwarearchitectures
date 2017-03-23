package org.se.lab.presentation.commands;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.se.lab.business.UserService;


public class DeleteCommand
	extends WebCommand
{
	private final Logger LOG = Logger.getLogger(DeleteCommand.class);
	
	@Override
	public void process() throws ServletException, IOException
	{
		LOG.debug("process DELETE command");
		
		Connection c = null;
		try
		{
			String id = req.getParameter("id");
		
			// TODO: Validate request parameters!
			
			c = createConnection();
			UserService service = factory.createUserService(c);
			service.removeUser(id);
			req.setAttribute("message", "User with id = " + id + " successfully deleted.");
		}
		catch(Exception e)
		{
		    req.setAttribute("message","Error: " + e.getMessage());
			LOG.error("Can't delete user!", e);
		}
		finally
		{
			if(c != null)
			{
				try
				{
					c.close();
				}
				catch (SQLException e)
				{
					req.setAttribute("message", "Error: " + e.getMessage());
					LOG.error("Can't close database connection!", e);
				}
			}
		}
		forward("/index.jsp");			
	}
}
