package org.se.lab.ws;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.se.lab.business.UserService;
import org.se.lab.data.User;

@WebService(serviceName="UserService", portName="UserServicePort")
public class UserServiceRemoteFacade 
{
	private final Logger logger = Logger.getLogger(UserServiceRemoteFacade.class);

	private UserService service;

	
	/*
	 * Life-cycle methods
	 */
	public UserServiceRemoteFacade() {}
	
	public UserServiceRemoteFacade(UserService service)
	{
		logger.debug("UserServiceRemoteFacade(" + service + ")");		
		this.service = service; 
	}
	
	@PostConstruct
    public void init()
    {
        logger.debug("init()");
    }

    @PreDestroy
    public void clean()
    {
        logger.debug("clean()");
    }

    
    /*
     * Web methods
     */
	
	@WebMethod
	public void addUser(String firstName, String lastName, String username, String password)
	{
		logger.debug("addUser(" + firstName + "," + lastName + "," + username + "," + password + ")");

		service.addUser(firstName, lastName, username, password);
	}

	@WebMethod
	public void removeUser(String idString)
	{
		logger.debug("removeUser(" + idString + ")");
		
		service.removeUser(idString);
	}

	@WebMethod
	public ArrayList<String> listUsers()
	{
		logger.debug("listUsers()");

		List<User> users = service.findAllUsers();
		ArrayList<String> names = new ArrayList<String>(); 
		for(User u: users)
		{
			names.add(u.getUsername());
		}
		
		return names;
	}
}