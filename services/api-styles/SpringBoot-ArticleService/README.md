# SpringBoot REST Service 

## Setup 

We can start the service as a separate process:
```
$ mvn spring-boot:run
```

## Accessing the API via curl

### Find Articles

```
$ curl -i http://localhost:8080/articles

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Wed, 26 Jul 2023 17:05:07 GMT

[
    {"id":1,"description":"LEGO 42122 Technic Jeep Wrangler 4x4 ","price":3473},
    {"id":2,"description":"Mould King 15025 Technik Muldenkipper","price":6956},
    {"id":3,"description":"CaDA Monster Truck","price":8999}
]
```

```
$ curl -i http://localhost:8080/articles/2

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Wed, 26 Jul 2023 17:06:08 GMT

{"id":2,"description":"Mould King 15025 Technik Muldenkipper","price":6956}
```

```
$ curl -i http://localhost:8080/articles/666

HTTP/1.1 404 
Content-Length: 0
Date: Wed, 26 Jul 2023 17:06:45 GMT
```

### Insert an Article
```
$ curl -i -X POST http://localhost:8080/articles -H 'Content-type:application/json' -d '{"description":"CaDA Master C61505W","price":21174}'

HTTP/1.1 201 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Wed, 26 Jul 2023 17:09:42 GMT

{"id":4,"description":"CaDA Master C61505W","price":21174}
```

### Update an Article
```
$ curl -i -X PUT http://localhost:8080/articles/1 -H 'Content-type:application/json' -d '{"id":1,"description":"LEGO 42122 Technic Jeep Wrangler 4x4 ","price":2200}'

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Wed, 26 Jul 2023 17:11:40 GMT

{"id":1,"description":"LEGO 42122 Technic Jeep Wrangler 4x4 ","price":2200}
```

### Delete an Article
```
$ curl -i -X DELETE http://localhost:8080/articles/2

HTTP/1.1 204 
Date: Wed, 26 Jul 2023 17:12:32 GMT
```

## References
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)

*Egon Teiniker, 2016 - 2023, GPL v3.0*
