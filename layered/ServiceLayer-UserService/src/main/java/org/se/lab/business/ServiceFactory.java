package org.se.lab.business;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.se.lab.data.UserDAOImpl;


public class ServiceFactory
{
    private final Logger logger = Logger.getLogger(ServiceFactory.class);
        
    public UserService createUserService(Connection connection)
    {
        logger.debug("createUserService(" + connection +")");
        
		UserDAOImpl userDAO = new UserDAOImpl(connection);		
		UserServiceImpl serviceImpl = new UserServiceImpl(connection);
		serviceImpl.setUserDAO(userDAO);
		
		UserService service = serviceImpl;

        logger.debug("    service: " + service);    
        return service;    
    }
    
    
    public UserService createUserService()
    {
        logger.debug("createUserService()");
        
        return createUserService(createConnection());
    }
    
        
    /*
     * Utility methods
     */
    
    protected Connection createConnection()
    {
        logger.debug("createConnection()");
        try
        {
        	Properties jdbcProperties = new Properties();
			jdbcProperties.load(new FileInputStream("jdbc.properties"));
			String driver = jdbcProperties.getProperty("jdbc.driver");
			String url = jdbcProperties.getProperty("jdbc.url");
			String user = jdbcProperties.getProperty("jdbc.username");
			String password = jdbcProperties.getProperty("jdbc.password");  

        	Class.forName(driver);
            Connection con = DriverManager.getConnection(url, user, password);
        
            return con;
        } 
        catch (SQLException | IOException | ClassNotFoundException e)
        {
            logger.debug("problem during creation of a database connection ", e);
            throw new IllegalStateException(e);
        }
    }
}
