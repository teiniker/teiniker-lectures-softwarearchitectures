# Example: Article Service Connector

A Service Connector is a component that provides a simple interface to interact 
with a web service. 

This example demonstrates how to create a Service Connector for an Article Service 
using Java's `HttpClient` and to use this connector to implement test cases for 
the service.


## Curl 


```bash
$ curl -i http://localhost:8080/articles

$ curl -i http://localhost:8080/articles/1
$ curl -i http://localhost:8080/articles/666

$ curl -i -X POST http://localhost:8080/articles -H 'Content-type:application/json' -d '{"description":"CaDA Master C61505W","price":21174}'

$ curl -i -X PUT http://localhost:8080/articles/1 -H 'Content-type:application/json' -d '{"id":1,"description":"LEGO 42122 Technic Jeep Wrangler 4x4 ","price":2200}'

$ curl -i -X DELETE http://localhost:8080/articles/2
```


## Java Client

```java