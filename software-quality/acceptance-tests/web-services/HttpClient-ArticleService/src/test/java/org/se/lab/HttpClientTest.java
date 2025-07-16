package org.se.lab;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class HttpClientTest
{
    // curl -i http://localhost:8080/articles/1
    @Test
    public void testGetRequestIdFound() throws IOException, InterruptedException
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/articles/1"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assert.assertEquals(200, response.statusCode());
        Map<String, List<String>> headers=  response.headers().map();
        Assert.assertEquals("application/json", headers.get("content-type").getFirst());
        Assert.assertEquals("application/json", response.headers().firstValue("content-type").orElse(""));

        Article article = fromJSON(response.body());
        Assert.assertEquals(1, article.getId());
        Assert.assertEquals("LEGO 42122 Technic Jeep Wrangler 4x4", article.getDescription());
        Assert.assertEquals(3473, article.getPrice());
    }

    // curl -i http://localhost:8080/articles/666
    @Test
    public void testGetRequestNotFound() throws IOException, InterruptedException
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/articles/666"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assert.assertEquals(404, response.statusCode());
    }

    //  curl -i http://localhost:8080/articles
    @Test
    public void testGetRequestAll() throws IOException, InterruptedException
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/articles"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("application/json", response.headers().firstValue("content-type").orElse(""));

        List<Article> articles = fromJSONArray(response.body());
        Assert.assertEquals(3, articles.size());
    }

    // curl -i -X POST http://localhost:8080/articles -H 'Content-type:application/json' -d '{"description":"CaDA Master C61505W","price":21174}'
    @Test
    public void testPostRequest() throws IOException, InterruptedException
    {
        HttpClient client = HttpClient.newHttpClient();
        Article article = new Article(0, "LEGO 42122 Technic Jeep Wrangler 4x4", 3473);
        String json = toJSON(article);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/articles"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assert.assertEquals(201, response.statusCode());
        Assert.assertEquals("application/json", response.headers().firstValue("content-type").orElse(""));

        Article createdArticle = fromJSON(response.body());
        Assert.assertTrue(createdArticle.getId() > 0);
        Assert.assertEquals(article.getDescription(), createdArticle.getDescription());
        Assert.assertEquals(article.getPrice(), createdArticle.getPrice());
    }

    // curl -i -X PUT http://localhost:8080/articles/1 -H 'Content-type:application/json' -d '{"id":1,"description":"LEGO 42122 Technic Jeep Wrangler 4x4 ","price":2200}'
    @Test
    public void testPutRequest() throws IOException, InterruptedException
    {
        HttpClient client = HttpClient.newHttpClient();
        Article article = new Article(1, "LEGO 42122 Technic Jeep Wrangler 4x4 ", 2200);
        String json = toJSON(article);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/articles/1"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("application/json", response.headers().firstValue("content-type").orElse(""));

        Article updatedArticle = fromJSON(response.body());
        Assert.assertEquals(article.getId(), updatedArticle.getId());
        Assert.assertEquals(article.getDescription(), updatedArticle.getDescription());
        Assert.assertEquals(article.getPrice(), updatedArticle.getPrice());
    }

    // curl -i -X DELETE http://localhost:8080/articles/2
    @Test
    public void testDeleteRequest() throws IOException, InterruptedException
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/articles/2"))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assert.assertEquals(204, response.statusCode());
        Assert.assertEquals("", response.body()); // No content expected
    }


    // Helper methods for JSON conversion

    public Article fromJSON(String json)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, Article.class);
        }
        catch (JsonProcessingException e)
        {
            throw new IllegalArgumentException("Invalid JSON string: " + json + "!",e);
        }
    }

    public List<Article> fromJSONArray(String json)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json,
                    mapper.getTypeFactory().constructCollectionType(List.class, Article.class));
        }
        catch (JsonProcessingException e)
        {
            throw new IllegalArgumentException("Invalid JSON string: " + json + "!",e);
        }
    }

    public String toJSON(Article article)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(article);
        }
        catch (JsonProcessingException e)
        {
            throw new IllegalArgumentException("Invalid Article object: " + article + "!",e);
        }
    }
}
