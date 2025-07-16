package org.se.lab;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class HttpClientTest
{
    private ArticleService service;

    @Before
    public void setup()
    {
        service = new ArticleServiceConnector("http://localhost:8080/articles");
    }

    // curl -i http://localhost:8080/articles/1
    @Test
    public void testGetRequestIdFound()
    {
        Article article = service.findById(1);

        Assert.assertEquals(1, article.getId());
        Assert.assertEquals("LEGO 42122 Technic Jeep Wrangler 4x4", article.getDescription());
        Assert.assertEquals(3473, article.getPrice());
    }

    // curl -i http://localhost:8080/articles/666
    @Test(expected = ServiceException.class)
    public void testGetRequestNotFound()
    {
        service.findById(666);
    }

    // curl -i http://localhost:8080/articles
    @Test
    public void testGetRequestAll() throws IOException, InterruptedException
    {
        List<Article> articles = service.findAll();

        Assert.assertEquals(3, articles.size());
    }

    // curl -i -X POST http://localhost:8080/articles -H 'Content-type:application/json' -d '{"description":"CaDA Master C61505W","price":21174}'
    @Test
    public void testPostRequest()
    {
        Article article = new Article(0, "LEGO 42122 Technic Jeep Wrangler 4x4", 3473);

        Article createdArticle = service.insert(article);

        Assert.assertTrue(createdArticle.getId() > 0);
        Assert.assertEquals(article.getDescription(), createdArticle.getDescription());
        Assert.assertEquals(article.getPrice(), createdArticle.getPrice());
    }

    // curl -i -X PUT http://localhost:8080/articles/1 -H 'Content-type:application/json' -d '{"id":1,"description":"LEGO 42122 Technic Jeep Wrangler 4x4 ","price":2200}'
    @Test
    public void testPutRequest()
    {
        Article article = new Article(1, "LEGO 42122 Technic Jeep Wrangler 4x4 ", 2200);

        Article updatedArticle = service.update(article, 1);

        Assert.assertEquals(article.getId(), updatedArticle.getId());
        Assert.assertEquals(article.getDescription(), updatedArticle.getDescription());
        Assert.assertEquals(article.getPrice(), updatedArticle.getPrice());
    }

    // curl -i -X DELETE http://localhost:8080/articles/2
    @Test
    public void testDeleteRequest()
    {
        service.delete(2);
    }
}
