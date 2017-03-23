How to access the Web application?
-------------------------------------------------------------------------------

Browser: http://localhost:8080/MVC-JSF



Anatomy of a simple JSF Web application
-------------------------------------------------------------------------------

Following the MVC architectural pattern, it is important to separate the 
presentation from the business logic. This allows both web designers and
programmers to focus on their core skills.

In the context of JSF, the application code is contained in beans (View Helper),
and the design is contained in the web pages (View). The JSF framework uses 
the javax.faces.webapp.FacesServlet as a Front Controller (see web.xml).


Managed Beans
-------------
A Java bean is a class that exposes properties and events to a framework,
such as JSF. 
A property is a named value of a given type that can be read and / or written.

A managed bean is a Java bean that can be accessed from JSF pages. A managed
bean must have a name and a scope.

Example: 
	@ManagedBean  
	@SessionScoped
	public class UserBean
		implements Serializable
	{
		//...
	}
@SessionScope means that the bean object (created by the JSF framework) is
available for one user across multiple pages (bound to the user's session).

The beans are "managed" in the following sense: When a bean name occurs in
a JSF page, the JSF implementation locates the object with that name, or
constructs it if it does not yet exist in the appropriate scope.

In JSF applications, we use managed beans for all data that needs to be
accessible from a page. The beans are the conduits between the user interface
and the backend of the application.


JSF Pages
---------
We need a JSF page for each browser screen. A JSF page is a XHTML file
with additional JSF tags.

JSF implementations defines a set of tags that are independent of HTML.
If we need such a tag in our XHTML page, we must add a namespace declaration.

Example:

<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:p="http://primefaces.org/ui">

	<!-- ... -->
</html>


A JSF page is similar to an HTML form. Note the following differences:
o) Our page must be properly formatted XHTML.
o) We use h:head, h:body, and h:form instead of head, body, and form.
o) Instead of using the familiar input HTML tags, we use h:inputText,
h:inputSecret, and h:commandButton.

The input field values are bound to properties of a bean.

Example:
	<h:inputText value="#{userBean.name}" />
	
	
How to build a JSF Web application using maven?
-------------------------------------------------------------------------------

1. Make sure that we build a WAR file
	<packaging>war</packaging>


2. Add the war-Plugin and specify the WAR file's name
	http://maven.apache.org/plugins/maven-war-plugin/war-mojo.html

	<build>
		<plugins>
			<plugin>
				<artifactId>maven-war-plugin</artifactId>
				<version>2.4</version>
				<configuration>
					<warSourceDirectory>WebContent</warSourceDirectory>
					<failOnMissingWebXml>false</failOnMissingWebXml>
				</configuration>
			</plugin>
		</plugins>

		<finalName>MVC-JSF-Login</finalName>
	</build>
			
			
3. Add dependencies for PrimeFaces

	<dependencies>
		<!-- Primefaces -->
		<dependency>
			<groupId>org.primefaces</groupId>
			<artifactId>primefaces</artifactId>
			<version>5.1</version>
		</dependency>

		<!-- JSF 2 API -->
		<dependency>
			<groupId>com.sun.faces</groupId>
			<artifactId>jsf-api</artifactId>
			<version>2.2.0-m07</version>
		</dependency>
	</dependencies>

	<repositories>
		<repository>
			<id>primefaces-repository</id>
			<name>Primefaces repository</name>
			<url>http://repository.primefaces.org</url>
		</repository>
	</repositories>

	Note that we also specify the location of the PrimeFaces repository!
		

How to use CDI for managed beans?
-------------------------------------------------------------------------------

Context and Dependency Injection (CDI) JSR299, defines a flexible model for 
beans that are managed by a JEE application server like JBoss.

These beans are bound to a context (such as the current request, a browser session,
or even a user-defined life cycle context). 

CDI specifies mechanisms for injecting beans, intercepting and decorating method 
calls, and firing and observing events.

Because CDI is a much more powerful mechanism than JSF managed beans, it makes
sense to use CDI beans if we deploy our application in a JEE application server.

Example:
	@Named  
	@SessionScoped
	public class UserBean
		implements Serializable
	{
		// ...
	} 
	
	Note that CDI comes with its own scope annotations, so we have to import:
		import javax.enterprise.context.SessionScoped;
		import javax.inject.Named;
		
	Note also, that session-scoped CDI beans must implement the Serializable interface.
	

How to use bean methods?
-------------------------------------------------------------------------------
We can invoke methods in value expressions by supplying the method name and
parameters.

Example:
	<h:commandButton value="Login" action="#{userBean.login()}" />
	
	Note that overloaded methods are not supported. The bean must have a unique
	method with the given name.
	

Where to get information about JSF?
-------------------------------------------------------------------------------
http://docs.oracle.com/javaee/7/tutorial/doc/jsf-intro.htm	 			
	 			
	 			
Where to get information about PrimeFaces?
-------------------------------------------------------------------------------	 			
http://www.primefaces.org/
http://www.primefaces.org/showcase/
	

			