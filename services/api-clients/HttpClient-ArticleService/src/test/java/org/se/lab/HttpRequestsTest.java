package org.se.lab;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class HttpRequestsTest 
{
    private HttpClient client;
    
    @Before
    public void setup() 
    {
        client = HttpClient.newBuilder()
                .build();

    }

    // curl -i -k http://localhost:8080/articles/2
	@Test
	public void testById() throws IOException, InterruptedException
    {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/articles/2"))
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int status = response.statusCode();
        String body = response.body();
        System.out.println("Status: " + status);
        System.out.println("Body: " + body);

        Assert.assertEquals(200, status);
		final String EXPECTED =
            "{\"id\":2,\"description\":" +
            "\"Mould King 15025 Technik Muldenkipper\",\"price\":6956}";
		Assert.assertEquals(EXPECTED, body);
	}

    // curl -i -k https://localhost:8443/articles/666
    @Test
    public void testById_NotFound() throws IOException, InterruptedException
    {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/articles/666"))
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int status = response.statusCode();
        String body = response.body();
        System.out.println("Status: " + status);
        System.out.println("Body: " + body);

        Assert.assertEquals(404, status);
    }

    // curl -i -k http://localhost:8080/articles
	@Test
	public void testAll() throws IOException, InterruptedException
    {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/articles"))
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int status = response.statusCode();
        String body = response.body();
        System.out.println("Status: " + status);
        System.out.println("Body: " + body);

		Assert.assertEquals(200, status);
	}

    // curl -i -X POST http://localhost:8080/articles -H 'Content-type:application/json' -d '{"description":"CaDA Master C61505W","price":21174}'
    @Test
    public void testInsert() throws IOException, InterruptedException
    {
        String jsonRequest =
            "{\"id\":5,\"description\":\"CaDA Master C61505W\",\"price\":21174}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/articles"))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();
     
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int status = response.statusCode();
        String body = response.body();
        System.out.println("Status: " + status);
        System.out.println("Body: " + body);

        Assert.assertEquals(201, status); 
    }

    // curl -i -X PUT http://localhost:8080/articles/1 -H 'Content-type:application/json' -d '{"id":1,"description":"LEGO 42122 Technic Jeep Wrangler 4x4 ","price":9999}'
    @Test
    public void testUpdate() throws IOException, InterruptedException
    {
        String jsonRequest =
            "{\"id\":1,\"description\":" +
            "\"LEGO 42122 Technic Jeep Wrangler 4x4 \",\"price\":9999}";
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/articles/1"))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")               
                .PUT(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());     
        int status = response.statusCode();
        String body = response.body();
        System.out.println("Status: " + status);
        System.out.println("Body: " + body);    
        
        Assert.assertEquals(200, status);
        Assert.assertTrue(body.contains("9999"));
    }

    // curl -i -k -X DELETE http://localhost:8080/articles/2
    @Test @Ignore
    public void testDelete() throws IOException, InterruptedException
    {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/articles/2"))
                .header("Accept", "application/json")
                .DELETE()
                .build();   

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());     
        int status = response.statusCode();
        String body = response.body();
        System.out.println("Status: " + status);
        System.out.println("Body: " + body);
        
        Assert.assertEquals(200, status);
    }
}
