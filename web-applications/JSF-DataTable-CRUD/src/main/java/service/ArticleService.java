package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import data.dao.ArticleDAO;
import data.model.Article;

@Stateless
public class ArticleService
{
	private final Logger LOG = Logger.getLogger(ArticleService.class);
	
	@Inject
	private ArticleDAO articleDAO;

	public List<Article> findAll()
	{
		try
		{
			List<Article> articles = articleDAO.findAll();
			LOG.debug("findAll: " + articles);
			return articles;
		} 
		catch (Exception e)
		{
			LOG.error("Can't find all Articles", e);
			throw new ServiceException("Can't find all Articles");
		}
	}

	public void add(Article article)
	{
		LOG.debug("add: " + article);
		try
		{
			articleDAO.insert(article);
		} 
		catch (Exception e)
		{
			LOG.error("Can't add Article " + article, e);
			throw new ServiceException("Can't add article: " + article);
		}
	}

	public void update(Article article)
	{
		LOG.debug("update: " + article);
		try
		{
			articleDAO.update(article);
		} 
		catch (Exception e)
		{
			LOG.error("Can't update Article " + article, e);
			throw new ServiceException("Can't update Article: " + article);
		}
	}

	public void delete(Article article)
	{
		LOG.debug("delete: " + article);
		try
		{
			articleDAO.delete(article);
		} 
		catch (Exception e)
		{
			LOG.error("Can't delete Article " + article, e);
			throw new ServiceException("Can't delete Article: " + article);
		}
	}

	public Article findById(long id)
	{
		LOG.debug("findById: " + id);
		try
		{
			Article article = articleDAO.findById(id);
			return article;
		} 
		catch (Exception e)
		{
			LOG.error("Can't find Article by id " + id, e);
			throw new ServiceException("Can't find Article by id: " + id);
		}
	}
}
