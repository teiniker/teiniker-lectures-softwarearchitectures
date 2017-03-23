package org.se.lab;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBException;

import org.junit.Assert;
import org.junit.Test;

public class VersionsITCase 
	extends AbstractRestServiceTest
{
	private final String WEB_APP_NAME = "/REST-IntegrationTest";
	
	@Test
	public void testFindById() throws IOException, JAXBException
	{
		URL url = new URL("http://" + HOST + ":" + PORT + WEB_APP_NAME + "/versions");
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/xml");
		
		int httpResponseCode = connection.getResponseCode();
		Assert.assertEquals(200, httpResponseCode);
		String content = readResponseContent(connection.getInputStream());	
		final String EXPECTED = 
				"<?xml version=\"1.0\"?>\n"
				+ "<ApiVersions xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
				+ "  <ApiVersionEntries resourceName=\"/workspaces\" deprecated=\"false\" versionName=\"v1\" />\n"
				+ "  <ApiVersionEntries resourceName=\"/sessions\" deprecated=\"false\" versionName=\"v1\" />\n"
				+ "</ApiVersions>\n";

	
		System.out.println("Response-Content:\n" + content);
		Assert.assertEquals(EXPECTED, content);
	}
}
