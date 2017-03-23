package org.se.lab.service;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.se.lab.data.User;
import org.se.lab.data.UserDAO;


@Stateless
@Remote(UserService.class)
public class UserServiceEJB
	implements UserService
{
	private final Logger LOG = Logger.getLogger(UserServiceEJB.class);
	
	@Inject
	private UserDAO dao;
		
	public UserServiceEJB()
	{
		LOG.info(UserServiceEJB.class.getName() + "() " + this);
	}
	
	
	public void addUser(String username, String password)
	{
		LOG.info("addUser( " + username + ")");
		String md5Password = PasswordEncoder.convertToMD5String(password);
		dao.createUser(username, md5Password);
	}

	
	public void removeUser(String idString)
	{
		LOG.info("delete: " + idString);
		
		// TODO
	}
	
	// Container managed transaction
	public List<UserDTO> findAllUsers()
	{
		LOG.info("findAllUsers()");
		// TODO: Exception handling
		List<User> list = dao.findAll();
		List<UserDTO> result = UserDTO.toUserDTOList(list);
		return result;
	}
	
	
	public UserDTO findById(int id) 
	{
		LOG.info("find User with id=" + id);

		User user = dao.findById(id);
		return new UserDTO(user);
	}
}
