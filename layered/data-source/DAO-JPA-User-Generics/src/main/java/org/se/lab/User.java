package org.se.lab;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = User.TABLE_NAME)
public class User
	implements Serializable
{
	private static final long serialVersionUID = 8685792371938184530L;

	public static final String TABLE_NAME = "TEST_USER";
	
    /*
	 * Constructors
	 */
    public User(String username, String password)
	{
		setUsername(username);
		setPassword(password);
	}
	
	protected User() //!!!
	{
	    this("","");
	}
	
	
	/*
	 * Property: id
	 */
	@Id
	@GeneratedValue
	private int id;
	public int getId()
	{
		return id;
	}
	
	
	/*
	 * Property: username
	 */	
	@Column(name="USER_NAME", length=64)
	private String username;
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		if(username == null)
			throw new IllegalArgumentException("Invalid parameter username!");
		this.username = username;
	}
	
	
	/*
	 * Property: password 
	 */
	private String password;
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		if(password == null)
			throw new IllegalArgumentException("Invalid parameter password!");
		this.password = password;
	}
	
	
	/*
	 * Object methods
	 */

    @Override
	public String toString()
	{
		return getId() + "," + getUsername() + "," + getPassword();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
