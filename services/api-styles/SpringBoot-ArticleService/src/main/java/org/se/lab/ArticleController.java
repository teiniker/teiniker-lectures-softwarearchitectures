package org.se.lab;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class ArticleController
{
    private static long sequence = 1;
    private static long nextValue()
    {
        return sequence++;
    }
    private final Map<Long, Article> table;

    ArticleController()
    {
        // Simulate database table
        table = new ConcurrentHashMap<>();
        long id1 = nextValue();
        table.put(id1, new Article(id1, "LEGO 42122 Technic Jeep Wrangler 4x4 ", 3473));
        long id2 = nextValue();
        table.put(id2, new Article(id2, "Mould King 15025 Technik Muldenkipper", 6956));
        long id3 = nextValue();
        table.put(id3, new Article(id3, "CaDA Monster Truck", 8999));
    }

    @GetMapping("/articles")
    ResponseEntity<List<Article>> findAll()
    {
        return new ResponseEntity<>(new ArrayList(table.values()), HttpStatus.OK);
    }

    @GetMapping("/articles/{id}")
    ResponseEntity<Article> indById(@PathVariable long id)
    {
        Article item = table.get(id);
        if(item == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(table.get(id), HttpStatus.OK);
    }

    @PostMapping("/articles")
    ResponseEntity<Article> insert(@RequestBody Article article)
    {
        article.setId(nextValue());
        table.put(article.getId(), article);
        return new ResponseEntity<>(article, HttpStatus.CREATED);
    }

    @PutMapping("/articles/{id}")
    ResponseEntity<?> update(@RequestBody Article article, @PathVariable long id)
    {
        Article item = table.get(id);
        if(item == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
        {
            item.setDescription(article.getDescription());
            item.setPrice(article.getPrice());
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
    }

    @DeleteMapping("/articles/{id}")
    ResponseEntity<?> delete(@PathVariable long id)
    {
        Article item = table.get(id);
        if(item == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
        {
            table.remove(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
