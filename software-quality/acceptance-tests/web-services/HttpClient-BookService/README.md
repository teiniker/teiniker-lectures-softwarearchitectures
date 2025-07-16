# Example: Article Service API Client


## Curl 


```bash
$ curl -i http://localhost:8080/books/2
$ curl -i http://localhost:8080/books/666

$ curl -i http://localhost:8080/books

$ curl -i -X POST http://localhost:8080/books -H 'Content-type:application/json' -d '{"author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"}'

$ curl -i -X PUT http://localhost:8080/books/1 -H 'Content-type:application/json' -d '{"author":"Joshua Bloch","title":"Effective Java, 2nd Edition","isbn":"978-0134685991"}'

$ curl -i -X DELETE http://localhost:8080/books/2
```


## Java Client

```java