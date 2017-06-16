package org.se.lab;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ArticlesITCase 
	extends AbstractRestServiceTest
{
	private final String WEB_APP_NAME = "/REST-EJB-ArticleService-IntegrationTest/v1";
	private static final JdbcTestHelper JDBC_HELPER = new JdbcTestHelper();
	
	@Before
	public void init()
	{
		JDBC_HELPER.executeSqlScript("src/test/resources/sql/createTable.sql");
	}
	
	@After
	public void destroy()
	{
		JDBC_HELPER.executeSqlScript("src/test/resources/sql/dropTable.sql");		
	}

	
	@Test
	public void testFindAll_XML() throws IOException, JAXBException
	{
		URL url = new URL("http://" + HOST + ":" + PORT + WEB_APP_NAME + "/articles");
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/xml");
		
		int httpResponseCode = connection.getResponseCode();
		Assert.assertEquals(200, httpResponseCode);
		String content = readResponseContent(connection.getInputStream());	
		final String EXPECTED = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<collection>"
				+ 	"<article id=\"1\" price=\"4295\">"
				+ 		"<description>Design Patterns</description>"
				+ 	"</article>"
				+ 	"<article id=\"2\" price=\"3336\">"
				+ 		"<description>Effective Java (2nd Edition)</description>"
				+ 	"</article>"
				+ "</collection>\n";

	
		System.out.println("Response-Content:\n" + content);
		Assert.assertEquals(EXPECTED, content);
	}

	@Test
	public void testFindById_XML() throws IOException, JAXBException
	{
		URL url = new URL("http://" + HOST + ":" + PORT + WEB_APP_NAME + "/articles/1");
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/xml");
		
		int httpResponseCode = connection.getResponseCode();
		Assert.assertEquals(200, httpResponseCode);
		String content = readResponseContent(connection.getInputStream());	
		final String EXPECTED = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ 	"<article id=\"1\" price=\"4295\">"
				+ 		"<description>Design Patterns</description>"
				+ 	"</article>\n";

	
		System.out.println("Response-Content:\n" + content);
		Assert.assertEquals(EXPECTED, content);
	}
}
