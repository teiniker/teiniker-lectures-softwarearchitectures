package org.se.lab.data;

import java.util.List;

import org.se.lab.data.Person;
import org.se.lab.data.PersonDAO;

class PersonDAOLogger // package-private
    implements PersonDAO
{ 
    /** Setter injection */
	private PersonDAO dao;
    public void setDao(PersonDAO dao)    
    {
        this.dao = dao;
    }
    

    public void insert(Person p)
    {
        System.out.println("PersonDAOLogger.insert(" + p + ")");
        dao.insert(p);
    }

    public void update(Person p)
    {
        System.out.println("PersonDAOLogger.update(" + p + ")");
        dao.update(p);
    }

    public void delete(long id)
    {
        System.out.println("PersonDAOLogger.delete(" + id + ")");
        dao.delete(id);
    }

    public Person findById(long id)
    {
        System.out.print("PersonDAOLogger.findById(" + id + ")");
        Person result = dao.findById(id);
        System.out.println(" = " + result);
        return result;        
    }
    
    public List<Person> findAll()
    {
        System.out.print("PersonDAOLogger.findAll()");
        List<Person> result = dao.findAll();
        System.out.println(" = " + result);
        return result;
    }
}
