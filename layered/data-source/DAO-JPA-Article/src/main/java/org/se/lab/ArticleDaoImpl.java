package org.se.lab;

import javax.persistence.EntityManager;

public class ArticleDaoImpl
	extends DaoImplTemplate<Article>
	implements ArticleDao
{
	/*
	 * Constructor
	 */
	public ArticleDaoImpl(EntityManager em)
	{
		super(em);
	}
	
	@Override 
	protected Class<Article> getEntityClass()
	{
		return Article.class;
	}
	
	
	/*
	 * Factory methods
	 */

	@Override
	public Article createArticle(String description, long price)
	{
		Article a = new Article();
		a.setDescription(description);
		a.setPrice(price);
		insert(a);
		return a;
	}
}
