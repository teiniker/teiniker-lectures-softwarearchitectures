package org.se.lab.server;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class SecurityUtils
{
	public static String getHashString(String s)
	{
		MessageDigest algorithm;
		byte[] defaultBytes;
		try
		{
			algorithm = MessageDigest.getInstance("SHA-512");
			defaultBytes = s.getBytes("UTF-8");
			algorithm.update(defaultBytes);
			byte[] bytes = algorithm.digest();
			String hash = convertToHexString(bytes);
			return hash;
		} 
		catch (NoSuchAlgorithmException e)
		{
			throw new SecurityUtilsException("Can't calculate the hash string.", e);
		} 
		catch (UnsupportedEncodingException e)
		{
			throw new SecurityUtilsException("Can't calculate the hash string.", e);
		}		
	}
	
	private static String convertToHexString(byte[] bytes)
	{
		StringBuilder hex = new StringBuilder();
		for(byte b : bytes)
		{
			int i = (b & 0xff); // copy the byte bit pattern into int value
			hex.append(String.format("%02x", i));
		}
		return hex.toString();
	}
}
