package org.se.lab;

import org.apache.commons.codec.binary.Hex;

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
    {
        if(s == null)
            throw new IllegalArgumentException();
        try
        {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte[] defaultBytes = s.getBytes("UTF-8");
            algorithm.update(defaultBytes);
            byte[] bytes = algorithm.digest();
            return bytes;
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException e)
        {
            throw new IllegalStateException("Hash convertion failure", e);

        }
    }


    @Override
	public String convertToHexString(byte[] bytes)
    {
        if(bytes == null)
            throw new IllegalArgumentException();

        return Hex.encodeHexString(bytes);
    }


    @Override
	public String convertToString(String s)
    {        
        if(s == null)
            throw new IllegalArgumentException();

        return Hex.encodeHexString(convertToBytes(s));
    }
}
