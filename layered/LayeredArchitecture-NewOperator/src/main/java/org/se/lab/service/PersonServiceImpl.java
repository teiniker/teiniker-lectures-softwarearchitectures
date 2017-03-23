package org.se.lab.service;

import java.util.List;

import org.se.lab.data.Person;
import org.se.lab.data.PersonDAOInMemoryImpl;

public class PersonServiceImpl 
{    
    /*
     * Constructor
     */
    public PersonServiceImpl()
    {
        dao = new PersonDAOInMemoryImpl();
    }

    /*
     * Composition: ---[1]-> dao:PersonDAOInMemoryImpl
     */
    private final PersonDAOInMemoryImpl dao;  
    
    
    /*
     * Business operations
     */
    
    public void save(Person p)
    {
    	dao.insert(p);
    }
    
    
    public Person load(long id)
    {
        
        return dao.findById(id);
    }
    
    
    public List<Person> load()
    {
        return dao.findAll();
    }
}
