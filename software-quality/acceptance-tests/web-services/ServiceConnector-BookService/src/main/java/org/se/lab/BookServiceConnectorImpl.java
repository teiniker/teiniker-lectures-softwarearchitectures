package org.se.lab;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class BookServiceConnectorImpl
    implements BookServiceConnector
{
    private final Logger LOG =
            Logger.getLogger(BookServiceConnectorImpl.class);

    private static final String BASE_URL = "http://localhost:8080/books";

    private final HttpClient client;

    public BookServiceConnectorImpl()
    {
        LOG.debug("BookServiceConnectorImpl initialized");
        client = HttpClient.newHttpClient();
    }

    @Override
    public Book findById(long id)
    {
        LOG.debug("Finding book with id: " + id);

        try
        {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/" + id))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(
                    request, HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();
            String body = response.body();
            if (status != 200)
                throw new IllegalStateException("Unexpected status code: " + status);
            else
                return convertJson2Book(body);
        }
        catch (IOException | InterruptedException e)
        {
            throw new IllegalStateException("Can't find book by id!", e);
        }
    }

    @Override
    public List<Book> findAll()
    {
        LOG.debug("Finding all books");

        try
        {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();
            String body = response.body();
            if (status != 200)
                throw new IllegalStateException("Unexpected status code: " + status);
            else
                return convertJsonArray2BookList(body);
        }
        catch (IOException | InterruptedException e)
        {
            throw new IllegalStateException("Can't find books!", e);
        }
    }

    @Override
    public Book insert(Book book)
    {
        LOG.debug("Inserting book: " + book);

        try
        {
            String jsonBody = convertBook2Json(book);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();
            String body = response.body();
            if (status != 200 && status != 201)
                throw new IllegalStateException("Unexpected status code: " + status);
            else
                return convertJson2Book(body);
        }
        catch (IOException | InterruptedException e)
        {
            throw new IllegalStateException("Can't insert a book!", e);
        }
    }

    @Override
    public Book update(Book book)
    {
        LOG.debug("Updating book: " + book);

        try
        {
            String jsonBody = convertBook2Json(book);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/" + book.id()))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();
            String body = response.body();
            if (status != 200)
                throw new IllegalStateException("Unexpected status code: " + status);
            else
                return convertJson2Book(body);
        }
        catch (IOException | InterruptedException e)
        {
            throw new IllegalStateException("Can't update a book!", e);
        }
    }

    @Override
    public void delete(long id)
    {
        LOG.debug("Deleting book with id: " + id);

        try
        {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/" + id))
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();
            if (status != 204)
                throw new IllegalStateException("Unexpected status code: " + status);
        }
        catch (IOException | InterruptedException e)
        {
            throw new IllegalStateException("Can't delete a book!", e);
        }
    }

    /*
     * JSON Serialization
     */

    protected Book convertJson2Book(String json)
    {
        Book book = null;
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            book = mapper.readValue(json, Book.class);
        }
        catch (JsonProcessingException e)
        {
            throw new IllegalStateException(e);
        }
        return book;
    }

    protected List<Book> convertJsonArray2BookList(String json)
    {
        List<Book> list = null;
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            list = mapper.readValue(json, new TypeReference<List<Book>>(){});
        }
        catch (JsonProcessingException e)
        {
            throw new IllegalStateException(e);
        }
        return list;
    }

    protected String convertBook2Json(Book book)
    {
        String json = null;
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(book);
        }
        catch (JsonProcessingException e)
        {
            throw new IllegalStateException(e);
        }
        return json;
    }
}
