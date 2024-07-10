package org.se.lab;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class ArticleController
{
    private final ArticleRepository repository;

    ArticleController(ArticleRepository repository)
    {
        this.repository = repository;
    }

    @GetMapping("/articles")
    List<Article> findAll()
    {
        return repository.findAll();
    }

    @GetMapping("/articles/{id}")
    Article findById(@PathVariable Long id)
    {
        return repository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id));
    }

    @PostMapping("/articles")
    Article insert(@RequestBody Article newArticle)
    {
        return repository.save(newArticle);
    }

    @PutMapping("/articles/{id}")
    Article update(@RequestBody Article newArticle, @PathVariable Long id)
    {
        return repository.findById(id)
                .map(employee -> {
                    employee.setDescription(newArticle.getDescription());
                    employee.setPrice(newArticle.getPrice());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newArticle.setId(id);
                    return repository.save(newArticle);
                });
    }

    @DeleteMapping("/articles/{id}")
    void delete(@PathVariable Long id)
    {
        repository.deleteById(id);
    }
}
