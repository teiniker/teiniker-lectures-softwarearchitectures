package org.se.lab.data;

import java.sql.Connection;

import org.apache.log4j.Logger;
import org.se.lab.business.UserDAO;


public class DataFactory
{
    private final Logger logger = Logger.getLogger(DataFactory.class);

    public UserDAO createUserDAO(Connection connection)
    {
        logger.debug("createUserDAO(" + connection + ")");

        return new UserDAOImpl(connection);
    }
}
