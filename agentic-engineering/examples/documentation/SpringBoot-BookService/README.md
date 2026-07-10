# SpringBoot REST - BookService 

TODO: In 3 to 5 sentences, give an overview about the example: A REST service implemented with SpringBoot.

See: agentic-engineering/examples/documentation/SpringBoot-BookService

## Setup 

TODO: Explain how to start this SpringBoot example.

## Accessing the API via curl

TODO: Explain curl in 3 sentences.

TODO: For the following sections, run the curl statements and explain the request and the response.

### Find Books

```
$ curl -i http://localhost:8080/books
```

```
$ curl -i http://localhost:8080/books/2
```

```
$ curl -i http://localhost:8080/books/666

```

### Insert a Book
```
$ curl -i -X POST http://localhost:8080/books -H 'Content-type:application/json' -d '{"author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"}'
```

### Update a Book
```
$ curl -i -X PUT http://localhost:8080/books/1 -H 'Content-type:application/json' -d '{"author":"Joshua Bloch","title":"Effective Java, 2nd Edition","isbn":"978-0134685991"}'
```

### Delete a Book
```
$ curl -i -X DELETE http://localhost:8080/books/2
```
```
$ curl -i http://localhost:8080/books/666
```

## Implementation

TODO: Generate a Mermaid class diagram from the given code.

TODO: Give an overview about the implementation of this service (including code snippets).



*Egon Teiniker, 2016 - 2026, GPL v3.0*
