package org.se.lab.data;

public class DAOFactory
{
	public PersonDAO createPersonDAO()
	{
		return new PersonDAOInMemoryImpl();
	}
	
	public PersonDAO createPersonDAOWithLogger()
	{
		PersonDAOInMemoryImpl impl = new PersonDAOInMemoryImpl();
		PersonDAOLogger logger = new PersonDAOLogger();
		logger.setDao(impl);
		return logger;
	} 
}
