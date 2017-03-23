package org.se.lab;

import java.util.List;

public interface UserDAO
{
    void insert(User p);
    void update(User p);
    void delete(long id);
    
    User findById(long id);
    List<User> findAll();
}
