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
        // TODO
    }

    // curl -i http://localhost:8080/books/666
    @Test
    public void testGetRequestNotFound() throws IOException, InterruptedException
    {
        // TODO
    }

    //  curl -i http://localhost:8080/books
    @Test
    public void testGetRequestAll() throws IOException, InterruptedException
    {
        // TODO
    }

    // curl -i -X POST http://localhost:8080/books -H 'Content-type:application/json' -d '{"author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"}'
    @Test
    public void testPostRequest() throws IOException, InterruptedException
    {
        // TODO
    }

    // curl -i -X PUT http://localhost:8080/books/1 -H 'Content-type:application/json' -d '{"author":"Joshua Bloch","title":"Effective Java, 2nd Edition","isbn":"978-0134685991"}'
    @Test
    public void testPutRequest() throws IOException, InterruptedException
    {
        // TODO
    }

    // curl -i -X DELETE http://localhost:8080/books/2
    @Test
    public void testDeleteRequest() throws IOException, InterruptedException
    {
        // TODO
    }


    // Helper methods for JSON conversion

    public Book fromJSON(String json)
    {
        // TODO
    }

    public List<Book> fromJSONArray(String json)
    {
        // TODO
    }

    public String toJSON(Book book)
    {
        // TODO
    }
}
