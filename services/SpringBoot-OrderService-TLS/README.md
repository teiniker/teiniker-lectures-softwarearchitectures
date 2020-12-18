# Spring Boot: OrderService TLS

We create a self-signed certificate with the `src/main/resources` directory, using the **keytool** shipped with the JDK:
```
$ keytool -genkey -keyalg RSA -alias medium -keystore medium.jks -storepass password -validity 365 -keysize 4096 -storetype pkcs12
```

Second, we have to change the `application.properties` file:
```
server.ssl.key-store=classpath:medium.jks
server.ssl.key-store-type=pkcs12
server.ssl.key-store-password=password
server.ssl.key-password=password
server.ssl.key-alias=medium
server.port=8443
```

Now, we start the **microservice**:
```
$ mvn spring-boot:run
```

We can send a **HTTP POST request**:
```
$ curl -i -k https://localhost:8443/orders -H 'Content-Type: application/json' --data-binary @- <<EOF
{
"items":[
    {
        "itemCode":"IT001",
        "quantity":3
    },
    {
        "itemCode":"IT004",
        "quantity":1
    }
    ],
    "shippingAddress":"No 4, CA, USA"
}
EOF

HTTP/1.1 201
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 19 Nov 2020 16:53:35 GMT

{"orderId":"28e9ba9c-f284-4f3b-ac14-8dce44f2a7cd","items":[{"itemCode":"IT001","quantity":3},{"itemCode":"IT004","quantity":1}],"shippingAddress":"No 4, CA, USA"}
```

By default, every TLS connection curl makes is verified to be secure.
Using `-k` (or `--insecure`) curl will not check the server's certificate.

And we can send a **HTTP GET request** using `curl`:
```
$ curl -i -k https://localhost:8443/orders/28e9ba9c-f284-4f3b-ac14-8dce44f2a7cd
HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 19 Nov 2020 16:55:48 GMT

{"orderId":"28e9ba9c-f284-4f3b-ac14-8dce44f2a7cd","items":[{"itemCode":"IT001","quantity":3},{"itemCode":"IT004","quantity":1}],"shippingAddress":"No 4, CA, USA"}
```

## References
* Prabath Siriwardena, Nuwan Dias. **Microservices Security in Action**. Manning, 2020
* [How to Secure a Spring Boot Application with TLS](https://medium.com/swlh/how-to-secure-a-spring-boot-application-with-tls-176062895559)
