package org.se.lab.server;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.log4j.Logger;


@WebService(name="UserService")
public class UserServiceRemoteFacade
{
	Logger log = Logger.getLogger(UserServiceRemoteFacade.class);
	
	private UserService service;

	public UserServiceRemoteFacade()
	{
		service = new UserServiceImpl();
	}

	
	@WebMethod
    public void addUser(String username, String password)
    {
		log.info("addUser(" + username + "," + password + ")");
		service.addUser(username, password);
    }

	
	@WebMethod
	public boolean login(String username, String password)
	{
		log.info("login(" + username + "," + password + ")");
		boolean result = service.login(username, password);
		return result;
	}
}