package data.dao;

import data.model.Article;

import java.util.List;

public interface ArticleDAO
{

	Article insert(Article article);

	Article update(Article article);

	void delete(Article article);

	Article findById(long id);

	List<Article> findAll();
}
