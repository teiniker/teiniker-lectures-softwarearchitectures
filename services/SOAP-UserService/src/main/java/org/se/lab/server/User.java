package org.se.lab.server;



public final class User
{
	/*
	 * Constructor
	 */
	public User(final String username, final String password)
	{
		// validate
		if(!isValidUsername(username))
			throw new IllegalArgumentException("invalid username " + username);
		if(!isValidPassword(password))
			throw new IllegalArgumentException("invalid password " + password);
		
		this.username = username;
		// hashing
		this.password = SecurityUtils.getHashString(password);
	}
	
	/*
	 * Property: username:String
	 */
	private final String username;
	public String getUsername()
	{
		return username;
	}
	
	/*
	 * Property: password:String
	 */
	private final String password;
	public String getPassword()
	{
		return password;
	}

	
	
	/*
	 * Object methods
	 */
	
	@Override
	public String toString()
	{
		return getUsername() + "," + getPassword();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		if (username == null)
		{
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}



	public static boolean isValidUsername(String username)
	{
		return Validator.checkString(username, "^[a-zA-Z0-9._]{4,32}$");
	}
	
	public static boolean isValidPassword(String password)
	{
		return Validator.checkString(password, "^[a-zA-Z0-9!$._]{8,16}$");
	}
}
