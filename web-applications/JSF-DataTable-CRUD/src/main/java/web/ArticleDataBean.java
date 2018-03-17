package web;

import data.model.Article;
import service.ArticleService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@SessionScoped
public class ArticleDataBean implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Inject
	private ArticleService articleService;

	/*
	 * Properties
	 */
	
	private List<Article> articles;
	public List<Article> getArticles()
	{
		return articles;
	}
	public void setArticles(List<Article> articles)
	{
		this.articles = articles;
	}

	private String name;
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}

	private double price;
	public double getPrice()
	{
		return price;
	}
	public void setPrice(double price)
	{
		this.price = price;
	}

	boolean editable;
	public boolean isEditable()
	{
		return editable;
	}
	public void setEditable(boolean editable)
	{
		this.editable = editable;
	}

	
	/*
	 * Actions
	 */

	public String addArticleAction()
	{
		Article article = new Article();
		article.setName(this.name);
		article.setPrice(this.price);
		articleService.add(article);
		return showArticlesAction();
	}

	public String deleteArticleAction(Article article)
	{
		if (article != null)
		{
			articleService.delete(article);
		}
		return showArticlesAction();
	}

	public String showArticlesAction()
	{
		articles = articleService.findAll();
		return "";
	}

	public String editArticleAction()
	{
		this.editable = !this.editable;
		return "";
	}

	public String updateArticlesAction()
	{
		for (Article article : articles)
		{
			articleService.update(article);
		}
		this.editable = false;
		return showArticlesAction();
	}
}
