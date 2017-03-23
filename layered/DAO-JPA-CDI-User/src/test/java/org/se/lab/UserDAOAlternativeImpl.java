package org.se.lab;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Alternative;

import org.apache.log4j.Logger;


@Alternative
public class UserDAOAlternativeImpl 
	implements UserDAO
{
	private final Logger LOG = Logger.getLogger(UserDAOAlternativeImpl.class);
	
    /*
     * Simulate the database table in a HashMap
     */
    private static List<User> table = new ArrayList<>();
    
    
    /*
     * DOA operations
     */
    
    public void insert(User user)
    {
    	LOG.debug("insert(" + user + ")");
        table.add(user);
    }

    public void update(User p)
    {
        // TODO
    }

    public void delete(long id)
    {
        // TODO
    }

    public User findById(long id)
    {
    	LOG.debug("findById(" + id + ")");
        for( User u : table)
        {
        	if(u.getId() == id)
        		return u;
        }
        return null;
    }
    
    public List<User> findAll()
    {
        return table;
    }
}
