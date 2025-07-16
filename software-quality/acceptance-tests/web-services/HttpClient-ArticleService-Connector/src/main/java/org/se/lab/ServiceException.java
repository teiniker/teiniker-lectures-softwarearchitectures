package org.se.lab;

public class ServiceException
    extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public ServiceException(String message, int statusCode)
    {
        super(message + " (HTTP status code: " + statusCode + ")");
    }

    public ServiceException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
