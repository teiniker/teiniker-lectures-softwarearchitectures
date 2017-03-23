package org.se.lab.service;

import java.util.List;

public interface UserService
{
	void addUser(String username, String password);

	void removeUser(final String idString);

	List<UserDTO> findAllUsers();
}
