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
    @Test
    public void testHttpClient() throws IOException, InterruptedException
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/articles/1"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assert.assertEquals(200, response.statusCode());
        Map<String, List<String>> headers=  response.headers().map();
        Assert.assertEquals("application/json", headers.get("content-type").get(0));
        Assert.assertEquals("application/json", response.headers().firstValue("content-type").orElse(""));

        Article article = fromJSON(response.body());
        Assert.assertEquals(1, article.getId());
        Assert.assertEquals("LEGO 42122 Technic Jeep Wrangler 4x4", article.getDescription());
        Assert.assertEquals(3473, article.getPrice());
    }





    // Helper methods for JSON conversion

    public Article fromJSON(String json)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            Article article = mapper.readValue(json, Article.class);
            return article;
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
