package org.se.lab.business;

import java.util.List;

public interface UserDAO
{
    void insert(User user);
    void update(User user);
    void delete(User user);

    User findById(int id);
    List<User> findAll();

    User createUser(String firstName, String lastName,
            String username, String password);
}
