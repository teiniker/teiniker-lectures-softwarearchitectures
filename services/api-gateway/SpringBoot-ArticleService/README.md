# Spring Boot REST Service

In this example, we can see how simple a RESTful service can be implemented with SpringBoot.

## Service Setup

We use Maven to compile and run the service.
```
$ mvn spring-boot:run
```

## Access the REST Service

Find all Articles:
```
$ curl -i http://localhost:8080/articles

```

Find a particular Article:
```    
$ curl -i http://localhost:8080/articles/2

```

Look for an unknown Article:
```    
$ curl -i http://localhost:8080/articles/99

```
   
Insert an Article:
```
$ curl -i -X POST http://localhost:8080/articles -H 'Content-type:application/json' -d '{"description": "Microservices Patterns: With examples in Java", "price": 2550}'

```

Update an Article:
```    
$ curl -i -X PUT http://localhost:8080/articles/2 -H 'Content-type:application/json' -d '{"description": "Effective Java", "price": 9999}'

```

Delete an Article:
```    
$ curl -i -X DELETE http://localhost:8080/articles/3
```

## Implementation

Spring Boot provides a very good support to building RESTful Web Services.

```Java
@RestController
public class ArticleController 
{
    private final ArticleRepository repository;

    ArticleController(ArticleRepository repository) 
    {
        this.repository = repository;
    }

    @GetMapping("/articles")
    List<Article> all() 
    {
        return repository.findAll();
    }

    @GetMapping("/articles/{id}")
    Article one(@PathVariable Long id) 
    {
        return repository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id));
    }
    //...
}
```

The following annotations can be used:
* The **@RestController** annotation is used to define the RESTful web services.
* The **@RequestMapping** annotation is used to define the Request URI to access the REST Endpoints.

* The **@GetMapping** annotation maps HTTP GET requests onto specific handler methods.
  It is a composed annotation that acts as a shortcut for `@RequestMapping(method = RequestMethod.GET)`.
* The **@PostMapping** annotation maps HTTP POST requests onto specific handler methods.
  It is a composed annotation that acts as a shortcut for `@RequestMapping(method = RequestMethod.POST)`.
* The  @PutMapping annotation maps HTTP PUT requests onto specific handler methods.
  It is a composed annotation that acts as a shortcut for `@RequestMapping(method = RequestMethod.PUT)`.
* The **@DeleteMapping** annotation maps HTTP DELETE requests onto specific handler methods.
  It is a composed annotation that acts as a shortcut for `@RequestMapping(method = RequestMethod.DELETE)`.

* The **@PathVariable** annotation is used to define the custom or dynamic request URI.
  The Path variable in request URI is defined as curly braces {}.
* The **@RequestParam** annotation is used to read the request parameters from the Request URL.
* The **@RequestBody** annotation is used to define the request body content type.

## References

* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

* Prabath Siriwardena, Nuwan Dias. **Microservices Security in Action**. Manning, 2020

*Egon Teiniker, 2020 - 2022, GPL v3.0*