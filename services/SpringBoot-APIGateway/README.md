# API Gateway

The API gateway is an important piece of infrastructure in our microservice architecture,
since it plays a critical role that helps us clearly separate the functional requirements 
from nonfunctional ones.

Microservices are behind a set of APIs that is exposed to the outside world via an API 
gateway.
The API gateway is the entry point to the microservice deployment, which screens all 
incoming messages for security and other QoS features.

One key aspect of microservices best practice is the 
**single responsibility principle (SRP)**.
Each microservice should be performing only one particular function.
An API gateway helps in **decoupling security from a microservice**.

## Setting Up Zuul 
Zuul is an open source proxy server build by Netflix, acting as the entry point for
all of the company's backend streaming applications.

To compile and run the **Zuul proxy** using Maven:
```
$ mvn spring-boot:run
```

Now we can access our microservice through the Zuul proxy:
```
$ curl -i http://localhost:9090/retail/orders/28e9ba9c-f284-4f3b-ac14-8dce44f2a7cd

HTTP/1.1 200
Date: Thu, 19 Nov 2020 17:06:04 GMT
Keep-Alive: timeout=60
Content-Type: application/json
Transfer-Encoding: chunked

{"orderId":"28e9ba9c-f284-4f3b-ac14-8dce44f2a7cd","items":[{"itemCode":"IT001","quantity":3},{"itemCode":"IT004","quantity":1}],"shippingAddress":"No 4, CA, USA"}
```
Note several important points in this request:
* The **port** has switched to **9090**, which is the port of the Zuul proxy.
* The request URL now starts with /retail, which is the base path in Zuul that 
we have configred to route requests to the microservice.

To see how the routing is configured, review the `src/main/resources/application.properties` 
file:
```
zuul.routes.retail.url=http://localhost:8080
zuul.sensitiveHeaders=
ribbon.eureka.enabled=false
server.port=9090
```

## References
Prabath Siriwardena, Nuwan Dias. **Microservices Security in Action**. Manning, 2020
