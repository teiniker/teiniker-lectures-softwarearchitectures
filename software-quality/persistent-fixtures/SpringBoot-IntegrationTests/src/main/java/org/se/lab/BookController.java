package org.se.lab;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/books")
public class BookController
{
    @Autowired  // Dependency Injection
    private BookRepository bookRepository;

    @GetMapping
    ResponseEntity<?> findAll()
    {
        return ResponseEntity.ok(bookRepository.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Book> findById(@PathVariable long id)
    {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent())
        {
            return ResponseEntity.ok(book.get());
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Book insert(@RequestBody Book book)
    {
        return bookRepository.save(book);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable long id, @RequestBody Book bookDetails)
    {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isPresent())
        {
            Book book = optionalBook.get();
            book.setAuthor(bookDetails.getAuthor());
            book.setTitle(bookDetails.getTitle());
            book.setIsbn(bookDetails.getIsbn());
            bookRepository.save(book);
            return ResponseEntity.ok(book);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id)
    {
        if(bookRepository.existsById(id))
        {
            bookRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }
}
