package org.se.lab;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named  
@SessionScoped
public class UserBean
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final Logger LOG = LoggerFactory.getLogger(UserBean.class);
	
	@Inject
	private PasswordEncoder encoder;
	
	
	/*
	 * Constructor
	 */
	public UserBean()
	{
		LOG.debug("UserBean()");
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
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	
	/*
	 * Property: password:String
	 */
	private String password;
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String plainText)
	{
		String password = encoder.convertToString(plainText);
		LOG.debug("setPassword(" + password + ")");
		this.password = password;
	}
	
	
	/*
	 * Actions
	 */
	
	public String login()
	{
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
