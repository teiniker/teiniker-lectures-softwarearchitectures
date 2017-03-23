How to access the WSDL definition?
-------------------------------------------------------------------------------

http://localhost:8080/SOAP-UserService/UserService?wsdl


How to Generate Client-Side Stubs
-------------------------------------------------------------------------------

$ pwd
SOAP-UserService

$ wsimport -verbose -Xnocompile -d ./target/classes -s src/gen/java -p org.se.lab.client http://localhost:8080/SOAP-UserService/UserService?wsdl

parsing WSDL...
Generating code...
Compiling code...

$ which wsimport 
/usr/java/jdk1.7.0_17/bin/wsimport

Note that we use the JDK's wsimport tool to generate the client-side classes 
because our JUnit test is running as a simple Java SE application.

  