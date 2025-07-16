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
    // curl -i http://localhost:8080/books/1
    @Test
    public void testGetRequestIdFound() throws IOException, InterruptedException
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/books/1"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assert.assertEquals(200, response.statusCode());
        Map<String, List<String>> headers=  response.headers().map();
        Assert.assertEquals("application/json", headers.get("content-type").getFirst());
        Assert.assertEquals("application/json", response.headers().firstValue("content-type").orElse(""));

        Book book = fromJSON(response.body());
        Assert.assertEquals(1, book.getId());
        Assert.assertEquals("Joshua Bloch", book.getAuthor());
        Assert.assertEquals("Effective Java", book.getTitle());
        Assert.assertEquals("978-0134685991", book.getIsbn());
    }

    // curl -i http://localhost:8080/books/666
    @Test
    public void testGetRequestNotFound() throws IOException, InterruptedException
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/books/666"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assert.assertEquals(404, response.statusCode());
    }

    //  curl -i http://localhost:8080/books
    @Test
    public void testGetRequestAll() throws IOException, InterruptedException
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/books"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("application/json", response.headers().firstValue("content-type").orElse(""));

        List<Book> books = fromJSONArray(response.body());
        Assert.assertEquals(3, books.size());
    }

    // curl -i -X POST http://localhost:8080/books -H 'Content-type:application/json' -d '{"author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"}'
    @Test
    public void testPostRequest() throws IOException, InterruptedException
    {
        HttpClient client = HttpClient.newHttpClient();
        Book book = new Book(0, "Robert C. Martin", "Clean Code", "978-0132350884");
        String json = toJSON(book);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/books"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assert.assertEquals(201, response.statusCode());
        Assert.assertEquals("application/json", response.headers().firstValue("content-type").orElse(""));

        Book createdBook = fromJSON(response.body());
        Assert.assertTrue(createdBook.getId() > 0);
        Assert.assertEquals(book.getAuthor(), createdBook.getAuthor());
        Assert.assertEquals(book.getTitle(), createdBook.getTitle());
        Assert.assertEquals(book.getIsbn(), createdBook.getIsbn());
    }

    // curl -i -X PUT http://localhost:8080/books/1 -H 'Content-type:application/json' -d '{"author":"Joshua Bloch","title":"Effective Java, 2nd Edition","isbn":"978-0134685991"}'
    @Test
    public void testPutRequest() throws IOException, InterruptedException
    {
        HttpClient client = HttpClient.newHttpClient();
        Book book = new Book(1, "Joshua Bloch", "Effective Java, 2nd Edition", "978-0134685991");
        String json = toJSON(book);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/books/1"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("application/json", response.headers().firstValue("content-type").orElse(""));

        Book updatedBook = fromJSON(response.body());
        Assert.assertEquals(book.getId(), updatedBook.getId());
        Assert.assertEquals(book.getAuthor(), updatedBook.getAuthor());
        Assert.assertEquals(book.getTitle(), updatedBook.getTitle());
        Assert.assertEquals(book.getIsbn(), updatedBook.getIsbn());
    }

    // curl -i -X DELETE http://localhost:8080/books/2
    @Test
    public void testDeleteRequest() throws IOException, InterruptedException
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/books/2"))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assert.assertEquals(204, response.statusCode());
        Assert.assertEquals("", response.body()); // No content expected
    }


    // Helper methods for JSON conversion

    public Book fromJSON(String json)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, Book.class);
        }
        catch (JsonProcessingException e)
        {
            throw new IllegalArgumentException("Invalid JSON string: " + json + "!",e);
        }
    }

    public List<Book> fromJSONArray(String json)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json,
                    mapper.getTypeFactory().constructCollectionType(List.class, Book.class));
        }
        catch (JsonProcessingException e)
        {
            throw new IllegalArgumentException("Invalid JSON string: " + json + "!",e);
        }
    }

    public String toJSON(Book book)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(book);
        }
        catch (JsonProcessingException e)
        {
            throw new IllegalArgumentException("Invalid Book object: " + book + "!",e);
        }
    }
}
