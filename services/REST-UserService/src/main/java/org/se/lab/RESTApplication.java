package org.se.lab;

import java.util.Set;
import java.util.HashSet;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/*")
public class RESTApplication 
	extends Application
{
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> classes = new HashSet<Class<?>>();

	public RESTApplication()
	{
//		singletons.add(new UserResourceImpl());
		classes.add(UserResourceImpl.class);
	}

	@Override
	public Set<Class<?>> getClasses()
	{
		return classes;
	}

	@Override
	public Set<Object> getSingletons()
	{
		return singletons;
	}
}
