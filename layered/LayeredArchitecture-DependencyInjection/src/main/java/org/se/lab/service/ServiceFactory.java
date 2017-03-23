package org.se.lab.service;

import org.se.lab.data.PersonDAO;


public class ServiceFactory
{    
    public PersonService createPersonService(PersonDAO dao)
    {
    	PersonServiceImpl impl = new PersonServiceImpl();
    	impl.setDao(dao);
    	return impl;
    }
}
