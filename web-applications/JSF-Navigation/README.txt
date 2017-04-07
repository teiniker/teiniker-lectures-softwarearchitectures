How to run the Web application on the command line?
-------------------------------------------------------------------------------

$ mvn wildfly:run


How to access the Web application?
-------------------------------------------------------------------------------

URL: http://localhost:8080/JSF-CDI


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
	
	

			