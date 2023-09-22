# SpringBoot REST Service (User Service)

## Setup 

We can start the service as a separate process:
```
$ mvn spring-boot:run
```

## Accessing the API via curl

### Find Books

```
$ curl -i http://localhost:8080/api/v1/users

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 27 Jul 2023 14:18:24 GMT

[
    {"id":1,"username":"homer","password":"2aaab795b3836904f82efc6ca2285d927aed75206214e1da383418eb90c9052f"},
    {"id":2,"username":"marge","password":"b4b811fa40505329ae871e52f03527c3720c9af7fb8607819658535c5484c41e"},
    {"id":3,"username":"bart","password":"9551dadbf76a27457946e70d1aebebe2132f8d3bce6378d216c11853524dd3a6"},
    {"id":4,"username":"lisa","password":"d84fe7e07bedb227cffff10009151d96fc944f6a1bd37cff60e8e4626a1eb1c3"}
]
```

```
$ curl -i http://localhost:8080/api/v1/users/2

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 27 Jul 2023 14:25:21 GMT

{"id":2,"username":"marge","password":"b4b811fa40505329ae871e52f03527c3720c9af7fb8607819658535c5484c41e"}
```

```
$ curl -i http://localhost:8080/api/v1/users/666

HTTP/1.1 404 
Content-Length: 0
Date: Thu, 27 Jul 2023 14:25:50 GMT
```

### Insert a Book
```
$ curl -i -X POST http://localhost:8080/api/v1/users -H 'Content-type:application/json' -d '{"username":"maggy","password":"055e7e7d9c267763a5e25b86c6cd0dd5231e991ca12bdd156da17b9730ebb344"}'

HTTP/1.1 201 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 27 Jul 2023 14:28:19 GMT

{"id":5,"username":"maggy","password":"055e7e7d9c267763a5e25b86c6cd0dd5231e991ca12bdd156da17b9730ebb344"}
```

### Update a Book
```
$ curl -i -X PUT http://localhost:8080/api/v1/users/1 -H 'Content-type:application/json' -d '{"id":1,"username":"homer","password":"duffbeer"}'

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 27 Jul 2023 14:30:17 GMT

{"id":1,"username":"homer","password":"duffbeer"}
```

### Delete a Book
```
$ curl -i -X DELETE http://localhost:8080/api/v1/users/2

HTTP/1.1 204 
Date: Thu, 27 Jul 2023 14:30:57 GMT
```

```
$ curl -i http://localhost:8080/api/v1/users/666

```
## Versioning

Add the following annotation to support the prefix `api/v1` for all links.
```Java
@RestController
@RequestMapping("api/v1")
public class UserController
{
    //...
}
```

## Swagger UI

```
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-ui</artifactId>
    <version>1.5.2</version>
</dependency>
```

So now our API documentation will be available at:
```
http://localhost:8080/swagger-ui.html
```


## References
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)

*Egon Teiniker, 2016 - 2023, GPL v3.0*
