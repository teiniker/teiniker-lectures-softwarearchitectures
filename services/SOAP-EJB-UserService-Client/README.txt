How to access the WSDL definition?
-------------------------------------------------------------------------------

Browser URL: http://localhost:8080/SOAP-EJB-UserService/UserService?wsdl

$ curl http://localhost:8080/SOAP-EJB-UserService/UserService?wsdl


How to Generate Client-Side Stubs?
-------------------------------------------------------------------------------

$ pwd
SOAP-EJB-UserService

$ wsimport -verbose -Xnocompile -s src/generated/java -p org.se.lab.client http://localhost:8080/SOAP-EJB-UserService/UserService?wsdl

parsing WSDL...
Generating code...
Compiling code...

$ which wsimport 
/usr/java/jdk1.8.0_60/bin/wsimport

Note that we use the JDK's wsimport tool to generate the client-side classes 
because our JUnit test is running as a simple Java SE application.
