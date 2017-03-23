package org.se.lab.service;

import java.util.List;

import org.se.lab.data.Person;
import org.se.lab.data.PersonDAO;
import org.se.lab.service.PersonService;

class PersonServiceImpl // package-private 
	implements PersonService 
{
    /* 
     * Setter injection
     * Association: ---[1]-> dao:PersonDAO 
     */
	private PersonDAO dao;
    public void setDao(PersonDAO dao)
    {
        if(dao == null)
            throw new NullPointerException();
        this.dao = dao;
    }
    
    
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
