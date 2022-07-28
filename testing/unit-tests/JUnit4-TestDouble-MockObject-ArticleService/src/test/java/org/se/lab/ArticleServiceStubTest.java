package org.se.lab;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ArticleServiceStubTest
{
	@Mock
	private ArticleTable table;

	private ArticleService service;
	
	@Before
	public void setup()
	{
		initMocks(this);
		service = new ArticleService(table);
	}
	
	@Test
	public void testAddArticle()
	{
		// setup
		Article book = new Article(1, "Effective Java", 3495);

		// exercise
		service.addArticle(book);
		
		// verify
	    // verify(table).insert(same(book));
        verify(table).insert(eq(new Article(1, "Effective Java", 3495)));
	}

	@Test
	public void testAddArticleTableException()
	{
		// setup
		doThrow(TableException.class).when(table).insert(any(Article.class));
		
		// exercise
		try
		{
			service.addArticle(new Article(1, "Effective Java", 3495));
			Assert.fail();
		}
		catch(ServiceException e)
		{
		    // verify
			Assert.assertTrue(e.getCause() instanceof TableException);
		}
	}
	
	@Test
	public void testNumberOfArticles()
	{
		// setup
        List<Article> articles = Arrays.asList(
				new Article(1, "Effective Java", 3495),
				new Article(2, "Java Concurrency in Practice", 3895),
				new Article(3, "Clean Code: A Handbook of Agile Software Craftsmanship", 3095));

		when(table.findAll()).thenReturn(articles);

		// exercise
		int number = service.numberOfArticles();
		
		// verify
		Assert.assertEquals(3, number);		
	}	
	
	
	@Test
	public void testNumberOfArticlesNullPointerException()
	{
		// setup
        when(table.findAll()).thenThrow(NullPointerException.class);
		
		// exercise
		try
		{
			service.numberOfArticles();
			Assert.fail();
		}
		catch(ServiceException e)
		{
		    // verify
			Assert.assertTrue(e.getCause() instanceof NullPointerException);
		}
	}

	
	@Test
	public void testNumberOfArticlesTableException()
	{
		// setup
        when(table.findAll()).thenThrow(TableException.class);

		// exercise
		try
		{
			service.numberOfArticles();
			Assert.fail();
		}
		catch(ServiceException e)
		{
            // verify
			Assert.assertTrue(e.getCause() instanceof TableException);
		}
	}
}
