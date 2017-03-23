package org.se.lab.data;

import java.util.List;

public interface PersonDAO
{
    void insert(Person p);
    void update(Person p);
    void delete(long id);
    
    Person findById(long id);
    List<Person> findAll();
}
