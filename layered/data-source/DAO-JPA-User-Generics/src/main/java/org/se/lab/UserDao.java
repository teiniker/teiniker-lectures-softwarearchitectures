package org.se.lab;


public interface UserDao
	extends DaoTemplate<User>
{
	User createUser(String username, String password);
}
