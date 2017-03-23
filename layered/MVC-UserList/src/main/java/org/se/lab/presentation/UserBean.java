package org.se.lab.presentation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.se.lab.business.ServiceFactory;
import org.se.lab.business.UserService;
import org.se.lab.data.User;


public class UserBean
	extends AbstractController
{
	private final Logger LOG = Logger.getLogger(UserBean.class);	
	private final ServiceFactory factory = new ServiceFactory();
	
	
	/*
	 * View-Helper Methods
	 */

	public String getUserTable()
	{
		LOG.debug("getUserTable()");
		
		Connection c = null;
		StringBuilder html = new StringBuilder();
		try
		{
			c = createConnection();
			UserService service = factory.createUserService(c);
			List<User> users = service.findAllUsers();			
			html.append("<table border=\"0\">");
			for (User user : users)
			{
				html.append("    <tr>");
				html.append("        <td width=\"150\">").append(user.getFirstname()).append("</td>");
				html.append("        <td width=\"150\">").append(user.getLastname()).append("</td>");
				html.append("        <td width=\"150\">").append(user.getUsername()).append("</td>");
				html.append("        <td width=\"150\">").append(user.getPassword()).append("</td>");
				html.append("        <td width=\"100\" align=\"center\"><form method = \"post\" action = \"/MVC-UserList/controller\"><input type = \"hidden\" name = \"id\" value = \"");
				html.append(user.getId()).append("\"><input type = \"submit\" name = \"action\" value = \"Delete\" /></form></td>");
				html.append("    </tr>").append("\n");
			}
			html.append("</table>");
		} 
		catch (Exception e)
		{
			// TODO: generate HTML message
			LOG.error("Can't create user HTML table", e);
		}
		finally
		{
			if(c != null)
				try
				{
					c.close();
				}
				catch (SQLException e)
				{
					// TODO: generate HTML message
					LOG.error("Can't close database connection!", e);
				}
		}
		return html.toString();
	}

	
	public String getTimeStamp()
	{
		LOG.debug("getTimeStamp()");
		
		Date timeStamp = new Date();
		return timeStamp.toString();
	}
}
