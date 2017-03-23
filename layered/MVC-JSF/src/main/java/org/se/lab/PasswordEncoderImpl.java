package org.se.lab;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class PasswordEncoderImpl 
	implements PasswordEncoder, Serializable
{   
	private static final long serialVersionUID = 1L;


	public PasswordEncoderImpl()
	{
		super();
	}
	
    @Override
	public byte[] convertToBytes(String s) 
        throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        if(s == null)
            throw new IllegalArgumentException();
        
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte[] defaultBytes = s.getBytes("UTF-8");
        algorithm.update(defaultBytes);
        byte[] bytes = algorithm.digest();
        return bytes;
    }

    
    @Override
	public String convertToHexString(byte[] bytes)
    {
        if(bytes == null)
            throw new IllegalArgumentException();
        
        StringBuffer hex = new StringBuffer();
        for (byte b : bytes)
        {
            int i = (b & 0xff); // copy the byte bit pattern into int value
            hex.append(String.format("%02x", i));
        }
        return hex.toString();
    }
    

    @Override
	public String convertToString(String s)
    {        
        if(s == null)
            throw new IllegalArgumentException();
        
        try
        {
            return convertToHexString(convertToBytes(s));
        } 
        catch (NoSuchAlgorithmException e)
        {
            throw new IllegalStateException("MD5 convertion failure", e);
           
        } 
        catch (UnsupportedEncodingException e)
        {
            throw new IllegalStateException("MD5 convertion failure",e);        
        }
    }
}
