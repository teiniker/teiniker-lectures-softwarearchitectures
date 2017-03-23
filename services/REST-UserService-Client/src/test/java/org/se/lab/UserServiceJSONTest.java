package org.se.lab;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBException;

import org.junit.Assert;
import org.junit.Test;

public class UserServiceJSONTest 
	extends AbstractTestBase
{
	private final String WEB_APP_NAME = "/REST-UserService/v1";
	
	
	@Test
	public void testFindById() throws IOException, JAXBException
	{
		URL url = new URL("http://" + HOST + ":" + PORT + WEB_APP_NAME + "/users/3");
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/json");
		
		int httpResponseCode = connection.getResponseCode();
		Assert.assertEquals(200, httpResponseCode);
		String content = readResponseContent(connection.getInputStream());	
		final String EXPECTED = "{\"id\":3,\"username\":\"bart\",\"password\":\"**********\"}\n";		
		System.out.println("Response-Content:\n" + content);
		Assert.assertEquals(EXPECTED, content);
	}
	
	
	@Test
	public void testFindAll() throws IOException, JAXBException
	{
		// Request
		URL url = new URL("http://" + HOST + ":" + PORT + WEB_APP_NAME + "/users");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/json");
	
		// Response
		int httpResponseCode = connection.getResponseCode();
		Assert.assertEquals(200, httpResponseCode);
		String content = readResponseContent(connection.getInputStream());	
		
		final String EXPECTED = 
				"[" +
					"{\"id\":1,\"username\":\"homer\",\"password\":\"**********\"}," +
					"{\"id\":2,\"username\":\"marge\",\"password\":\"**********\"}," +
					"{\"id\":3,\"username\":\"bart\",\"password\":\"**********\"}," +
					"{\"id\":4,\"username\":\"lisa\",\"password\":\"**********\"}" +
				"]\n";
		System.out.println("Response-Content:\n" + content);
		Assert.assertEquals(EXPECTED, content);
	}


	@Test
	public void testFindAllFromIndex() throws IOException, JAXBException
	{
		URL url = new URL("http://" + HOST + ":" + PORT + WEB_APP_NAME + "/users?index=1&size=2");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/json");
	
		// Response
		int httpResponseCode = connection.getResponseCode();
		Assert.assertEquals(200, httpResponseCode);
		String content = readResponseContent(connection.getInputStream());	
		
		final String EXPECTED = 
				"[" +
					"{\"id\":2,\"username\":\"marge\",\"password\":\"**********\"}," +
					"{\"id\":3,\"username\":\"bart\",\"password\":\"**********\"}" +
				"]\n";
		System.out.println("Response-Content:\n" + content);
		Assert.assertEquals(EXPECTED, content);
	}
}
