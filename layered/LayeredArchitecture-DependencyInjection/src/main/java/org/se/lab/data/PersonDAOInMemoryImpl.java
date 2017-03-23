package org.se.lab.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class PersonDAOInMemoryImpl // package-private
    implements PersonDAO
{
    /*
     * Simulate the database table in a HashMap
     */
    private static Map<Long,Person> personTable = new HashMap<Long,Person>();
    
    
    /*
     * DOA operations
     */
    
    public void insert(Person p)
    {
        personTable.put(p.getId(), p);
    }

    public void update(Person p)
    {
        personTable.put(p.getId(), p);
    }

    public void delete(long id)
    {
        personTable.remove(id);
    }

    public Person findById(long id)
    {
        return personTable.get(id);        
    }
    
    public List<Person> findAll()
    {
        return new ArrayList<Person>(personTable.values());
    }
}
