# REST Service + Database 

TODO: Example under construction...

## Setup

We can start the service as a separate process:
```
$ mvn spring-boot:run
```


### Find Articles

```
$ curl -i http://localhost:8080/api/v1/articles

```

```
$ curl -i http://localhost:8080/api/v1/articles/2

```

```
$ curl -i http://localhost:8080/api/v1/articles/99

```

### Insert an Article
```
$ curl -i -X POST http://localhost:8080/api/v1/articles -H 'Content-type:application/json' -d '{"description": "Microservices Patterns: With examples in Java", "price": 2550}'

```

### Update an Article
```
$ curl -i -X PUT http://localhost:8080/api/v1/articles/2 -H 'Content-type:application/json' -d '{"description": "Effective Java", "price": 9999}'

```

### Delete an Article
```
$ curl -i -X DELETE http://localhost:8080/api/v1/articles/3

```

```
$ curl -i -k http://localhost:8080/api/v1/articles/3

```

## References


*Egon Teiniker, 2017-2023, GPL v3.0*
