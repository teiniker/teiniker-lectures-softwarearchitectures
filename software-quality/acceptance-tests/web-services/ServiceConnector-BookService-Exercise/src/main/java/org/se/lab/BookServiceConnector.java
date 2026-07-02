package org.se.lab;

import java.util.List;

public interface BookServiceConnector
{
    Book insert(Book book);
    Book update(Book book);
    void delete(long id);
    Book findById(long id);
    List<Book> findAll();
}
