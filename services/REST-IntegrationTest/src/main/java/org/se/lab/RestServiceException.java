package org.se.lab;

public class RestServiceException
	extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public RestServiceException(String msg)
	{
		super();
	}

	public RestServiceException(String msg, Throwable t)
	{
		super(msg, t);
	}
}
