# Example: SpringBoot REST + JPA

## Build and Run 

Make sure the MariaDB database is running: 
```Bash
$ sudo systemctl start mariadb.service 
```

If we start the MySQL client, wie can monitor the database content:
```
$ mysql -ustudent -pstudent
MariaDB [(none)]> use testdb;

MariaDB [testdb]> show tables;
MariaDB [testdb]> select * from book;	
```

We can start the service as a separate process:
```Bash
$ mvn spring-boot:run
```

## Accessing the REST API via curl

### Insert Books
```Bash
$ curl -X POST http://localhost:8080/api/books  -H "Content-Type: application/json" -d '{"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"}'
$ curl -X POST http://localhost:8080/api/books  -H "Content-Type: application/json" -d '{"id":2,"author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"}'
$ curl -X POST http://localhost:8080/api/books  -H "Content-Type: application/json" -d '{"id":3,"author":"Martin Fowler","title":"Refactoring","isbn":"978-0134757599"}'
```

### Find Books

```Bash
$ curl -i http://localhost:8080/api/books
```

```Bash
$ curl -i http://localhost:8080/api/books/3

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 09 Feb 2025 09:54:02 GMT

{
    "id":3,
    "author":"Martin Fowler",
    "title":"Refactoring",
    "isbn":"978-0134757599"
}
```

### Update a Book

```Bash
$ curl -i -X PUT http://localhost:8080/api/books/3  -H "Content-Type: application/json" -d '{"id":3,"author":"Martin Fowler","title":"Refactoring Existing Code","isbn":"978-0134757599"}'

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 09 Feb 2025 09:51:26 GMT

{
    "id":3,
    "author":"Martin Fowler",
    "title":"Refactoring Existing Code",
    "isbn":"978-0134757599"
}
```


### Delete a Book

```Bash
$ curl -i -X DELETE http://localhost:8080/api/books/2

HTTP/1.1 200 
Content-Length: 0
Date: Sun, 09 Feb 2025 09:53:13 GMT
```


## Repositories

In Spring Boot, **Repositories** are Java interfaces that provide the mechanism for storage, 
retrieval, update, delete and search operation on objects. Instead of writing complex data 
access code, **Spring Data JPA** generates it automatically for us.

_Example:_ Repository to store Book objects in a database.
```Java
public interface BookRepository extends JpaRepository<Book, Long>
{
    // Additional query methods (if needed) can be defined here.
}
```

* `BookRepository extends JpaRepository<Book, Long>`
    - `Book`: The type of **entity** this repository manages.
    - `Long`: The type of the **entity’s primary key (ID)**.

* When we extend `JpaRepository`, Spring Data JPA automatically implements common 
    CRUD operations for us:
    - `findAll()`
    - `findById(Long id)`
    - `save(Book book)`
    - `delete(Book book)`
    - and many more...

    We do not need to write the actual SQL or JPQL queries for these basic operations.

* SpringBoot will create a bean for our `BookRepository` and inject it wherever we need 
    it (e.g., in `BookController`).
    ```Java
    @Autowired
    private BookRepository bookRepository;
    ```

    We can simply call `bookRepository.save(myBook)` or `bookRepository.findAll()`.

* Custom Queries: If we need queries more complex than the standard CRUD, we 
    can define them as methods in the repository interface.


*Egon Teiniker, 2016-2025, GPL v3.0*
