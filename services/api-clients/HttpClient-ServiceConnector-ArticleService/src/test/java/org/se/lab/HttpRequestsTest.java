package org.se.lab;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class HttpRequestsTest 
{
    private ArticleServiceConnector service;

    @Before
    public void setup()
    {
        service = new ArticleServiceConnectorImpl();
    }


    // curl -i -k http://localhost:8080/articles
	@Test
	public void testById() 
    {
        Article article = service.findById(2);
        
        Assert.assertEquals(Long.valueOf(2), article.id());
        Assert.assertEquals("Mould King 15025 Technik Muldenkipper", article.description());
        Assert.assertEquals(6956, article.price());
	}


    // curl -i http://localhost:8080/articles/666
    @Test(expected = IllegalStateException.class)
    public void testById_NotFound()
    {
        Article article = service.findById(666);
        Assert.assertNull(article);
    }


    // curl -i -k http://localhost:8080/articles
	@Test
	public void testAll() 
    {
        List<Article> articles = service.findAll();
        
        Assert.assertEquals(3, articles.size());

        // {"id":1,"description":"LEGO 42122 Technic Jeep Wrangler 4x4","price":3473}
        Assert.assertEquals(Long.valueOf(1), articles.get(0).id());
        Assert.assertEquals("LEGO 42122 Technic Jeep Wrangler 4x4", articles.get(0).description());
        Assert.assertEquals(3473, articles.get(0).price());

        // {"id":2,"description":"Mould King 15025 Technik Muldenkipper","price":6956}   
        Assert.assertEquals(Long.valueOf(2), articles.get(1).id());
        Assert.assertEquals("Mould King 15025 Technik Muldenkipper", articles.get(1).description());
        Assert.assertEquals(6956, articles.get(1).price());
    
        // {"id":3,"description":"CaDA Monster Truck","price":8999}
        Assert.assertEquals(Long.valueOf(3), articles.get(2).id());
        Assert.assertEquals("CaDA Monster Truck", articles.get(2).description());
        Assert.assertEquals(8999, articles.get(2).price());
    }

    
    @Test
    public void testInsert()
    {
        Article article = new Article(0L, "CaDA Master C61505W", 21174);
        Article insertedArticle = service.insert(article);

        Assert.assertTrue(insertedArticle.id() > 0);
        Assert.assertEquals("CaDA Master C61505W", insertedArticle.description());
        Assert.assertEquals(21174, insertedArticle.price());
    }

    @Test
    public void testUpdate()
    {
        Article article = new Article(1L, "LEGO 42122 Technic Jeep Wrangler 4x4", 9999);
        service.update(article);

        Article updatedArticle = service.findById(1);
        Assert.assertNotNull(updatedArticle);
        Assert.assertEquals(9999, updatedArticle.price());
    }

    @Test @Ignore
    public void testDelete()
    {
        service.delete(2);
    }
}
