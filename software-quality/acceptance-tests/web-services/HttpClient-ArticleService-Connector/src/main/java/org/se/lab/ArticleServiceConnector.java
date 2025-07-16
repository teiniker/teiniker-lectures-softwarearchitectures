package org.se.lab;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ArticleServiceConnector implements ArticleService
{
    private final String BASE_URL;
    private final HttpClient client = HttpClient.newHttpClient();

    public ArticleServiceConnector(String baseUrl)
    {
        BASE_URL = baseUrl;
    }

    @Override
    public Article findById(long id)
    {
        try
        {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/" + id))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200)
            {
                return fromJSON(response.body());
            }
            else
            {
                throw new ServiceException("Article not found with id: " + id, response.statusCode());
            }
        }
        catch (IOException | InterruptedException e)
        {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Article> findAll()
    {
        try
        {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200)
            {
                return fromJSONArray(response.body());
            }
            else
            {
                throw new ServiceException("Articles not found: ", response.statusCode());
            }
        }
        catch (IOException | InterruptedException e)
        {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Article insert(Article article)
    {
        try
        {
            String json = toJSON(article);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 201)
            {
                return fromJSON(response.body());
            }
            else
            {
                throw new ServiceException("Article not inserted", response.statusCode());
            }
        }
        catch (IOException | InterruptedException e)
        {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Article update(Article article, long id)
    {
        try
        {
            String json = toJSON(article);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/" + id))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200)
            {
                return fromJSON(response.body());
            }
            else
            {
                throw new ServiceException("Article not updated with id: " + id, response.statusCode());
            }
        }
        catch (IOException | InterruptedException e)
        {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void delete(long id)
    {
        try
        {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/" + id))
                    .DELETE()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 204)
            {
                throw new IllegalStateException("Delete failed, status: " + response.statusCode());
            }
        }
        catch (IOException | InterruptedException e)
        {
            throw new RuntimeException(e);
        }
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
