package org.se.lab.business;

import java.util.List;


public interface UserService
{
	void addUser(String firstName, String lastName, String username, String password);

	void removeUser(final String idString);

	List<UserDTO> findAllUsers();
}