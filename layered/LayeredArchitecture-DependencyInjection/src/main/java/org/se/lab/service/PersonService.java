package org.se.lab.service;

import java.util.List;

import org.se.lab.data.Person;

public interface PersonService
{
    void save(Person p);
    
    Person load(long id);
    List<Person> load();
}