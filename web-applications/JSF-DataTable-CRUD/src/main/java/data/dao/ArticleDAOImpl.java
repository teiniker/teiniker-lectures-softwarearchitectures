package data.dao;

import data.model.Article;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ArticleDAOImpl implements ArticleDAO
{
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Article insert(Article article)
	{
		entityManager.persist(article);
		return article;
	}

	@Override
	public Article update(Article article)
	{
		return entityManager.merge(article);
	}

	@Override
	public void delete(Article article)
	{
		Article a = findById(article.getId());
		entityManager.remove(a);
	}

	@Override
	public Article findById(long id)
	{
		return entityManager.find(Article.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Article> findAll()
	{
		final String hql = "SELECT a FROM " + Article.class.getName() + " AS a";
		return entityManager.createQuery(hql).getResultList();
	}
}
