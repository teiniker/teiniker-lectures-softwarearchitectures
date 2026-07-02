# Spring Cloud Gateway

Spring Cloud Gateway aims to provide a simple way to route to APIs and provide cross cutting concerns 
to them such as: security, monitoring/metrics, and resiliency.

* **Route**: The basic building block of the gateway. It is defined by an ID, a destination URI, 
  a collection of predicates, and a collection of filters. 
  A route is matched if the aggregate predicate is true.

* **Predicate**: This is a Java 8 Function Predicate. The input type is a Spring Framework ServerWebExchange. 
  This lets you match on anything from the HTTP request, such as headers or parameters.

* **Filter**: These are instances of Spring Framework GatewayFilter that have been constructed with 
  a specific factory. Here, you can modify requests and responses before or after sending the downstream request.


## Setup

Set up the microservice: 
```
$ cd SpringBoot-BackendService
$ mvn spring-boot:run

$ curl -i http://localhost:9090/books
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 07 Feb 2023 18:54:12 GMT

[
  {"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"},
  {"id":2,"author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"},
  {"id":3,"author":"Martin Fowler","title":"Refactoring","isbn":" 978-0134757599"}
]
```

Set up the API gateway:

```
$ cd SpringBoot-API-Gateway
$ mvn spring-boot:run
```

## Path Route Predicate
```
spring:
  cloud:
    gateway:
      routes:
        - id: path_route
          uri: http://localhost:9090
          predicates:
            - Path=/v1/**
          filters:
            - RewritePath=/v1/(?<segment>.*), /$\{segment}

logging:
  level:
    org:
      springframework:
        cloud:
          gateway: TRACE
```

**The route definition**:

- `id: path_route`: Just a human-readable identifier for the route. 
  It has no functional effect, it shows up in logs.

- `uri: http://localhost:9090`: The downstream target. 
  Any request matching this route gets forwarded to a service running 
  locally on port 9090.

- `Path=/v1/**`: The predicate that decides *whether* the route applies. 
  It matches any incoming request whose path starts with `/v1/`, 
  including nested paths (`**` matches multiple segments), 
  e.g. `/v1/users`, `/v1/orders/42/items`.

- `RewritePath=/v1/(?<segment>.*), /$\{segment}`: A filter that modifies  
  the request *before* forwarding. It's a regex replacement:
  - `/v1/(?<segment>.*)` matches the path and captures everything after 
    `/v1/` into a named group called `segment`.
  - `/$\{segment}` is the replacement. The backslash is YAML escaping so 
    Spring's property resolution doesn't try to interpolate `${segment}` 
    as a config placeholder at load time; at runtime it becomes the regex 
    replacement `/${segment}`.
  
  Net effect: the `/v1` prefix is stripped. A request to the gateway at 
  `GET /v1/users/42` is forwarded as `GET http://localhost:9090/users/42`. 
  This is the classic pattern when the gateway exposes a versioned public 
  API but the backend service doesn't know about the version prefix.


**The logging block**:

Sets the logger for the `org.springframework.cloud.gateway` package to 
`TRACE`, the most verbose level. We'll see detailed output about route 
matching (which predicates were evaluated and whether they matched), 
filter chain execution, and request forwarding. 

Useful while developing or debugging why a route isn't matching, we would 
normally remove it or dial it back to `INFO` in production, since TRACE is 
noisy and adds overhead.

## Examples

Here are a few examples of how to access the service through the API Gateway:

```
$ curl -i http://localhost:8080/v1/books
HTTP/1.1 200 OK
transfer-encoding: chunked
Content-Type: application/json
Date: Tue, 07 Feb 2023 19:12:18 GMT

[
  {"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"},
  {"id":2,"author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"},
  {"id":3,"author":"Martin Fowler","title":"Refactoring","isbn":" 978-0134757599"}
]
```

```
$ curl -i http://localhost:8080/v1/books/1
HTTP/1.1 200 OK
transfer-encoding: chunked
Content-Type: application/json
Date: Tue, 07 Feb 2023 19:12:14 GMT

{"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"}
```

The path `/v1/books` will be rewritten to: `/books`.




## References
* [YouTube: Use Spring Cloud Gateway to drive traffic to your APIs](https://youtu.be/wYk0JrNdb8g)
* [Spring Cloud Tutorial - Spring Cloud Gateway Hello World Example](https://www.javainuse.com/spring/cloud-gateway)
* [Spring Cloud Gateway](https://cloud.spring.io/spring-cloud-gateway/reference/html)

*Egon Teiniker, 2016-2026, GPL v3.0*