package org.se.lab;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class BookServiceTest
{
    private BookServiceConnector service;

    @Before
    public void setup()
    {
        service = new BookServiceConnectorImpl();            
    }

    // curl -ik http://localhost:8080/books/2
    @Test
    public void testById()
    {
        Book book = service.findById(2);

        Assert.assertEquals(2L, book.id());
        Assert.assertEquals("Robert C. Martin", book.author());
        Assert.assertEquals("Clean Code", book.title());
        Assert.assertEquals("978-0132350884", book.isbn());
    }


    // curl -ik http://localhost:8080/books/666
    @Test(expected = IllegalStateException.class)
    public void testByIdNotFound()
    {
        service.findById(666);
    }


    // curl -ik http://localhost:8080/books
    @Test
    public void testAll()
    {
        List<Book> books = service.findAll();

        Assert.assertEquals(3, books.size());

        // {"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"}
        Assert.assertEquals(1L, books.get(0).id());
        Assert.assertEquals("Joshua Bloch", books.get(0).author());
        Assert.assertEquals("Effective Java", books.get(0).title());
        Assert.assertEquals("978-0134685991", books.get(0).isbn());

        // {"id":2,"author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"}
        Assert.assertEquals(2L, books.get(1).id());
        Assert.assertEquals("Robert C. Martin", books.get(1).author());
        Assert.assertEquals("Clean Code", books.get(1).title());
        Assert.assertEquals("978-0132350884", books.get(1).isbn());

        // {"id":3,"author":"Martin Fowler","title":"Refactoring","isbn":"978-0134757599"}
        Assert.assertEquals(3L, books.get(2).id());
        Assert.assertEquals("Martin Fowler", books.get(2).author());
        Assert.assertEquals("Refactoring", books.get(2).title());
        Assert.assertEquals("978-0134757599", books.get(2).isbn());
    }


    // curl -ik -X POST http://localhost:8080/books \
    //   -H 'Content-type:application/json' \
    //   -d '{"author":"Robert C. Martin","title":"Clean Code",
    //        "isbn":"978-0132350884"}'
    @Test
    public void testInsert()
    {
        Book newBook = new Book(
                0L, "Robert C. Martin", "Clean Code", "978-0132350884");
        Book createdBook = service.insert(newBook);

        Assert.assertTrue(createdBook.id() > 0);
        Assert.assertEquals(newBook.author(), createdBook.author());
        Assert.assertEquals(newBook.title(), createdBook.title());
        Assert.assertEquals(newBook.isbn(), createdBook.isbn());
    }


    // curl -ik -X PUT http://localhost:8080/books/1 \
    //   -H 'Content-type:application/json' \
    //   -d '{"author":"Joshua Bloch","title":"Effective Java, 2nd Edition",
    //        "isbn":"978-0134685991"}'
    @Test
    public void testUpdate()
    {
        Book updatedBook = new Book(
                1L, "Joshua Bloch", "Effective Java, 2nd Edition",
                "978-0134685991");
        Book returnedBook = service.update(updatedBook);

        Assert.assertEquals(updatedBook.id(), returnedBook.id());
        Assert.assertEquals(updatedBook.author(), returnedBook.author());
        Assert.assertEquals(updatedBook.title(), returnedBook.title());
        Assert.assertEquals(updatedBook.isbn(), returnedBook.isbn());
    }


    // curl -ik -X DELETE http://localhost:8080/books/2
    @Ignore
    @Test
    public void testDelete()
    {
        service.delete(2);
    }
}
