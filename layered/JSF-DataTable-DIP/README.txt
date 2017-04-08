How to setup the database?
-------------------------------------------------------------------------------

1) Start DB Server

$ su
root66
# systemctl start mariadb.service


2) Create table and insert test data

$ cd JSF-DataTable
$ mysql -u student -p 
student
> use testdb;
> source src/main/resources/sql/createTable.sql

Tipp: Have a look into src/main/resources/META-INF/persistence.xml


How to start the Web application from the command line?
-------------------------------------------------------------------------------

$ cd JSF-DataTable
$ mvn wildfly:run


How to access the Web application?
-------------------------------------------------------------------------------

URL: http://localhost:8080/JSF-DataTable-DIP


How to use the Dependency Inversion Principle (DIP)?
-------------------------------------------------------------------------------

In a regular Layered Architecture, all dependencies between layers are pointing 
in the same direction:

	Presentation Layer --> Domain Layer --> Data Source Layer
	
All Entity classes are used as pure data containers (annotated with JPA) and put 
into the Data Source Layer.

In Domain-Driven Design (DDD), we use Entity classes also for holding business
logic. Thus, we create a real object-oriented model of the problem domain.
To test this Domain Layer independent of the Data Source Layer we have to use
the well known Dependency Inversion Principle (DIP).

We put the Entity classes and Repository interfaces (a.k.a. DAO interfaces) into
the Domain Layer. The Data Source Layer depends on the Domain Layer and implements 
the given interfaces. 
 
		Presentation Layer --> Domain Layer <-- Data Source Layer

Note: To completely isolate a domain layer from the persistence layer we have
	to remove all JPA annotations and use an XML mapping file instead.
	
