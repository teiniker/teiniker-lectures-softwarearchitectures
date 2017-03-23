package org.se.lab.server;

public interface UserService
{
	void addUser(String username, String password);
	
	boolean login(String username, String password);	
}
