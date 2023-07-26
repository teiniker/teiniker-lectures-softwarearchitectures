package org.se.lab;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ArticleDaoTest
{
	private final static JpaTestHelper JPA_HELPER = new JpaTestHelper();
	private final EntityManager em = JPA_HELPER.getEntityManagerForPersistenceUnit("test");

	private final List<Integer> ids = new ArrayList<>();
    private ArticleDao dao;
       
    @Before
    public void setUp()
    {
        dao = new ArticleDaoImpl(em);
        
        JPA_HELPER.begin();
        
        Article book1 = new Article("Design Patterns", 4295);
        dao.insert(book1);
        ids.add(book1.getId());
        
        Article book2 = new Article("Effective Java (2nd Edition)", 3336);
        dao.insert(book2);
        ids.add(book2.getId());
        
        em.flush();
        em.clear();
    }   

    @After
    public void tearDown()
    {
    	JPA_HELPER.rollback();
    }


    @Test
    public void testFindById()
    {
        Article book = dao.findById(ids.get(0));
        
        Assert.assertEquals("Design Patterns", book.getDescription());
        Assert.assertEquals(4295, book.getPrice());
    }
    
    @Test
    public void testFindAll()
    {
	    List<Article> books = dao.findAll();
	    
        Assert.assertEquals(2,books.size());
        Assert.assertEquals("Design Patterns", books.get(0).getDescription());
        Assert.assertEquals("Effective Java (2nd Edition)", books.get(1).getDescription());
        for(Article book : books)
        {
            System.out.println(book.getId() + ", " + book.getDescription() + ", "
                    + book.getPrice());
        }
    }

    
	@Test
	public void testUpdate()
	{
		Article book = dao.findById(ids.get(0));

		book.setPrice(2250);
		dao.update(book);

		em.flush();
		em.clear();

		Article a = dao.findById(ids.get(0));
		Assert.assertEquals("Design Patterns", a.getDescription());
		Assert.assertEquals(2250, a.getPrice());
	}

	
	@Test
	public void testDelete()
	{
		Article book = dao.findById(ids.get(0));
		dao.delete(book);
	    
		em.flush();
		em.clear();

		Article a = dao.findById(ids.get(0));
	    Assert.assertNull(a);
	}
}
