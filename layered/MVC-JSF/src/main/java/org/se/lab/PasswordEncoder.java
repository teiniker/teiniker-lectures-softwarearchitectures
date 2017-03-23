package org.se.lab;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public interface PasswordEncoder
{

	public abstract byte[] convertToBytes(String s)
			throws NoSuchAlgorithmException, UnsupportedEncodingException;

	public abstract String convertToHexString(byte[] bytes);

	public abstract String convertToString(String s);

}