package org.se.lab;

import java.util.List;

public interface ArticleService
{
    Article findById(long id);
    List<Article> findAll();

    Article insert(Article article);
    Article update(Article article, long id);
    void delete(long id);
}
