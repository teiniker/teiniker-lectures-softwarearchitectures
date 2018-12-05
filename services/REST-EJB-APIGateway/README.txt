How to setup the services?
-------------------------------------------------------------------------------

1) SOAP-EJB-UserService
    $ cd SOAP-EJB-UserService
    $ mvn wildfly:deploy
    create database schema and insert test data
        => src/test/resources/sql

2) REST-EJB-ArticleService
    $ REST-EJB-ArticleService
    $ mvn wildfly:deploy
    create database schema and insert test data
        => src/test/resources/sql

3) REST-EJB-APIGateway
    $ REST-EJB-APIGateway
    $ curl http://localhost:8080/SOAP-EJB-UserService/UserService?wsdl > src/main/resources/wsdl/UserService.wsdl
    $ mvn wildfly:deploy


How to access the REST resource?
-------------------------------------------------------------------------------

URL: http://localhost:8080/REST-EJB-APIGateway/v1/articles


TODO: Combine Article and User to a DTO for the Service Gateway


JAX-RS Client API
---------------------------------------------------------------------
https://jersey.java.net/documentation/latest/client.html

A fluent Java based API for communication with RESTful Web services. 
This standard API that is part of Java EE 7 is designed to make it 
easy to consume a Web service exposed via HTTP protocol and enables 
developers to concisely and efficiently implement portable client-side 
solutions that leverage existing and well established client-side HTTP 
connector implementations.



How to find a single article?
---------------------------------------------------------------------
GET http://localhost:8080/REST-EJB-APIGateway/v1/articles/1 HTTP/1.1
Accept-Encoding: gzip,deflate
Accept: application/json
Host: localhost:8080
Connection: Keep-Alive
User-Agent: Apache-HttpClient/4.1.1 (java 1.5)

HTTP/1.1 200 OK
Connection: keep-alive
X-Powered-By: Undertow/1
Server: WildFly/10
Content-Type: application/json
Content-Length: 53
Date: Thu, 10 Nov 2016 14:36:31 GMT

{"id":1,"description":"Design Patterns","price":4295}


How to find all articles?
---------------------------------------------------------------------
GET http://localhost:8080/REST-EJB-APIGateway/v1/articles HTTP/1.1
Accept-Encoding: gzip,deflate
Accept: application/json
Host: localhost:8080
Connection: Keep-Alive
User-Agent: Apache-HttpClient/4.1.1 (java 1.5)


HTTP/1.1 200 OK
Connection: keep-alive
X-Powered-By: Undertow/1
Server: WildFly/10
Content-Type: application/json
Content-Length: 178
Date: Thu, 10 Nov 2016 14:38:36 GMT

[{"id":1,"description":"Design Patterns","price":4295},{"id":2,"description":"Effective Java (2nd Edition)","price":3336},{"id":100,"description":"Design Patterns","price":4295}]

