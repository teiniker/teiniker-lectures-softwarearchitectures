package org.se.lab;


public interface ArticleDao
	extends DaoTemplate<Article>
{
	Article createArticle(String description, long price);
}
