How to implement a RESTful Web Service?
-------------------------------------------------------------------------------

	1. Create a dynamic Web Project
		Runtime: JBoss
	
	2. Create a RESTFul Web Service
		New/Other/WebServices/Create a Sample RESTful Web Service
		=> generated: web.xml
		=> generated: Application.java
		=> implement: User.java
		=> implement: UserResource.java
		=> implement: UserResourceImpl.java
		
	3. Add it to the Server
	
	4. Access it from the remote test client


1. Insert
-------------------------------------------------------------------------------

To create an object with POST, the client sends a representation of the new
object it is creating to the parent URI of its representation, leaving out the 
numeric target ID.

The service receives the POST message, processes the XML, and creates a new 
order in the database using a database-generated unique ID.

HTTP requires that if POST creates a new resource that it respond with a code
of 201 "Created".
The Location header in the response message provides a URI to the client so it 
knows where to further interact with the object that was created.

Example: 

	POST /RestTest/users HTTP/1.1
	Content-Type: application/xml
	Accept: application/xml
	User-Agent: Java/1.7.0_25
	Host: localhost:8180
	Proxy-Connection: keep-alive
	Content-Length: 133
	
	<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	<user id="0">
		<username>maggie</username>
		<password>**********</password>
	</user>
	
	
	HTTP/1.1 201 Created
	Server: Apache-Coyote/1.1
	Location: http://localhost:8180/RestTest/users/5
	Content-Length: 0
	Date: Mon, 15 Sep 2014 07:46:06 GMT



2. Update
-------------------------------------------------------------------------------
We will model updating objects using the HTTP PUT method.
The client PUTs a new representation of the object it is updating to the exact
URI location that represents the object.

The PUT method is idempotent, no matter how many times we transmit this PUT
request, the underlying object will still have the same final state.

When a resource is updated with PUT, the HTTP specification requires that the 
server sends a response code of 200 "OK" and a response message body or a 
response code of 204 "No Content" without any response body.

Example:

	PUT /RestTest/users/1 HTTP/1.1
	Content-Type: application/xml
	Accept: application/xml
	User-Agent: Java/1.7.0_25
	Host: localhost:8180
	Proxy-Connection: keep-alive
	Content-Length: 131
	
	<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	<user id="1">
		<username>homer</username>
		<password>xxxxxxxxx</password>
	</user>
	
	
	HTTP/1.1 204 No Content
	Server: Apache-Coyote/1.1
	Date: Thu, 04 Sep 2014 19:27:33 GMT

In case of an invalid id:

	HTTP/1.1 404 Not Found
	Connection: keep-alive
	X-Powered-By: Undertow/1
	Server: WildFly/8
	Content-Length: 0
	Date: Mon, 15 Sep 2014 08:42:21 GMT



Delete
-------------------------------------------------------------------------------
We use the HTTP DELETE method to remove objects.

The client simply invokes the DELETE method on the exact URI that represents 
the object we want to remove.

When a resource is removed with DELETE, the HTTP specification requires that 
the server sends a response code of 200 "OK" and a response message body or a 
response code of 204 "No Content" without any response body.

Example: 

	DELETE /RestTest/users/2 HTTP/1.1
	User-Agent: Java/1.7.0_25
	Host: localhost:8180
	Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2
	Proxy-Connection: keep-alive
	
	
	HTTP/1.1 204 No Content
	Server: Apache-Coyote/1.1
	Date: Thu, 04 Sep 2014 18:33:40 GMT

In the case of an invalid id:

	HTTP/1.1 404 Not Found
	Connection: keep-alive
	X-Powered-By: Undertow/1
	Server: WildFly/8
	Content-Length: 0
	Date: Mon, 15 Sep 2014 08:46:42 GMT


Find By Id
-------------------------------------------------------------------------------
A URI pattern is used to obtain individual objects.

We use the HTTP GET method to retrive individual objects from the server.

The response code is 200 "OK" indicating that the request was successful.
The Content-Type header specifies the format of our message body as XML.

Example:

	GET /RestTest/users/3 HTTP/1.1
	Accept: application/xml
	User-Agent: Java/1.7.0_25
	Host: localhost:8180
	Proxy-Connection: keep-alive
	
	
	HTTP/1.1 200 OK
	Server: Apache-Coyote/1.1
	Content-Type: application/xml
	Content-Length: 131
	Date: Thu, 04 Sep 2014 18:47:38 GMT
	
	<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	<user id="3">
		<username>bart</username>
		<password>**********</password>
	</user>

In the case of an invalid id:

	HTTP/1.1 404 Not Found
	Connection: keep-alive
	X-Powered-By: Undertow/1
	Server: WildFly/8
	Content-Length: 0
	Date: Mon, 15 Sep 2014 08:38:24 GMT



Find All
-------------------------------------------------------------------------------
To get a list of objects the remote client will call an HTTP GET on the URI of
the object group it is interested in.

The response code is 200 "OK" indicating that the request was successful.
The Content-Type header specifies the format of our message body as XML.

Example: 

	GET /RestTest/users HTTP/1.1
	Accept: application/xml
	User-Agent: Java/1.7.0_25
	Host: localhost:8180
	Proxy-Connection: keep-alive
	
	
	HTTP/1.1 200 OK
	Server: Apache-Coyote/1.1
	Content-Type: application/xml
	Content-Length: 309
	Date: Thu, 04 Sep 2014 18:51:26 GMT
	
	<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	<collection>
		<user id="1">
			<username>homer</username>
			<password>**********</password>
		</user>
		<user id="3">
			<username>bart</username>
			<password>**********</password>
		</user>
		<user id="4">
			<username>lisa</username>
			<password>**********</password>
		</user>
	</collection>


The problem with this bulk operation is that we may have thousands of objects
in our system and we may overload our client and hurt our response times.
To mitigate this problem, we will allow the client to specify query parameters 
on the URI to limit the size of the dataset returned.
These parameters will be optional.

Example:

	GET /RestTest/users?index=1&size=2 HTTP/1.1
	Accept: application/xml
	User-Agent: Java/1.7.0_25
	Host: localhost:8180
	Proxy-Connection: keep-alive

	
	HTTP/1.1 200 OK
	Server: Apache-Coyote/1.1
	Content-Type: application/xml
	Content-Length: 232
	Date: Thu, 04 Sep 2014 18:56:18 GMT
	
	<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	<collection>
		<user id="3">
			<username>bart</username>
			<password>**********</password>
		</user>
		<user id="4">
			<username>lisa</username>
			<password>**********</password>
		</user>
	</collection>
