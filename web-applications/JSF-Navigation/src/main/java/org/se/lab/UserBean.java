package org.se.lab;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.log4j.Logger;

@Named  
@SessionScoped
public class UserBean
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private final Logger LOG = Logger.getLogger(UserBean.class);
	
	/*
	 * Constructor
	 */
	public UserBean()
	{
		LOG.debug("Create: " + UserBean.class.getSimpleName());
	}
	
	@PostConstruct
	public void init()
	{
		LOG.debug("init()");
	}
	
	
	/*
	 * Property: name:String
	 */
	private String name;
	public String getName()
	{
		LOG.debug("getName():" + name);
		return name;
	}
	public void setName(String name)
	{
		LOG.debug("setName(" + name + ")");
		this.name = name;
	}
	
	
	/*
	 * Property: password:String
	 */
	private String password;
	public String getPassword()
	{
		LOG.debug("getPassword():" + password);
		return password;
	}
	public void setPassword(String password)
	{
		LOG.debug("setPassword(" + password + ")");
		this.password = password;
	}
	
	
	/*
	 * Actions
	 */
	
	public String login()
	{
		LOG.debug("login()");
		if(getName().equals("teini"))
		{
			return "welcome";			
		}
		else
		{
			return "loginFailed";
		}
	}
}
