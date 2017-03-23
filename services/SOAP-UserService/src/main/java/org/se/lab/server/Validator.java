package org.se.lab.server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator
{
	public static boolean checkString(String s, String regex)
	{
		if(s == null || regex == null)
			throw new IllegalArgumentException();
		
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(s);
	
		return m.matches();
	}
}
