# Example: MVC UserForm

## Setup
We build and run the application like any Spring-Boot application:

```bash
$ mvn spring-boot:run
```

The running web application can be accessed by a browser:
```
URL: http://localhost:8080/index.html
```

## Interception Proxy

Use an **interception proxy** to review the HTTP request:

```
POST https://localhost:8443/controller HTTP/1.1
host: localhost:8443
User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:102.0) Gecko/20100101 Firefox/102.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8
Accept-Language: en-US,en;q=0.5
Referer: https://localhost:8443/index.html
Content-Type: application/x-www-form-urlencoded
Content-Length: 46
Origin: https://localhost:8443
Connection: keep-alive

id=7&username=marge&password=morebeer%21%21%21
```

## Curl 

Use curl as another client:

```bash
$ curl -ki -X POST -d 'id=1&username=homer&password=love4duffbeer' https://localhost:8443/controller
```


*Egon Teiniker, 2016-2026, GPL v3.0*