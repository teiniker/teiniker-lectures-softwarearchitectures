Weld Contexts and Dependency Injection
-------------------------------------------------------------------------------
http://weld.cdi-spec.org/

Weld is the reference implementation of CDI: Contexts and Dependency Injection for 
the Java EE Platform which is the Java standard for dependency injection and 
contextual lifecycle management and one of the most important and popular parts of 
the Java EE platform.

Weld is integrated into many Java EE application servers such as WildFly, JBoss 
Enterprise Application Platform, GlassFish, Oracle WebLogic and others. 
Weld can also be used in a Servlet-only environment (Tomcat, Jetty) or plain Java 
SE environment.

Also see:
http://blog.rocketscience.io/dependency-injection-with-cdi-in-java-se/
http://www.laliluna.com/articles/2011/01/12/jboss-weld-jpa-hibernate.html
http://stackoverflow.com/questions/20935977/what-is-the-easiest-way-to-have-cdi-and-jpa-in-java-se
http://stackoverflow.com/questions/20166218/how-to-manage-entitymanager-life-cycle-in-cdi-environement-using-tomcat


How to inject beans?
-------------------------------------------------------------------------------
In order to use the beans you create, you inject them into yet another bean 
that can then be used by an application, such as a JavaServer Faces application. 

public class CDITest extends CDITestBase
{
	...
	
	@Inject
	UserDAO dao;

	@Inject
	EntityManager em;
	
	...	
}	

 
How to use bean EL names?
-------------------------------------------------------------------------------
To make a bean accessible through the EL, use the @Named built-in qualifier:

Example: 
	@Named("MyPrinter")
	@RequestScoped
	public class Printer { ... }
	
The @Named qualifier allows you to access the bean by using the bean name, with 
the first letter in lowercase. For example, a Facelets page would refer to the 
bean as myPrinter.


How to configure a CDI application?
-------------------------------------------------------------------------------
CDI uses an optional deployment descriptor named beans.xml. 

Like other Java EE deployment descriptors, the configuration settings in 
beans.xml are used in addition to annotation settings in CDI classes. 

The settings in beans.xml override the annotation settings if there is a conflict.

For a web application, the beans.xml deployment descriptor, if present, must be 
in the WEB-INF directory. For EJB modules or JAR files, the beans.xml deployment 
descriptor, if present, must be in the META-INF directory.


How to use alternative implementations in CDI applications?
-------------------------------------------------------------------------------
When you have more than one version of a bean (more implementation classes for
an interface) that you use for different purposes, you can choose between them 
during the development phase by injecting one qualifier or another.

To make a bean available for lookup, injection, or EL resolution using this 
mechanism, give it a javax.enterprise.inject.Alternative annotation and then use 
the alternatives element to specify it in the beans.xml file.

Example:

	public interface UserDAO
	{
	    void insert(User p);
	    void update(User p);
	    void delete(long id);
	    
	    User findById(long id);
	    List<User> findAll();
	}

	// Note that the original version is NOT annotated!
	public class UserDAOImpl 
	implements UserDAO
	{
		...
	}
	
	@Alternative
	public class UserDAOAlternativeImpl 
		implements UserDAO
	{
		...
	}
	
	<alternatives>
		<class>org.se.lab.UserDAOAlternativeImpl</class>
	</alternatives>
	
	Run CDITest to execute the alternative methods.
	
	
How to use Decorators in CDI applications?
-------------------------------------------------------------------------------
A decorator is a Java class that is annotated javax.decorator.Decorator and 
that has a corresponding decorators element in the beans.xml file.

A decorator bean class must also have a delegate injection point, which is 
annotated javax.decorator.Delegate. This injection point can be a field, a 
constructor parameter, or an initializer method parameter of the decorator 
class.

Example:
	
	@Decorator
	public class UserDAODecorator
	implements UserDAO
	{
		private final Logger LOG = Logger.getLogger(UserDAODecorator.class);
		
		@Inject 
		private EntityManager em;
		
		@Inject @Delegate
		private UserDAO dao;
	
		
		@Override
		public void insert(User user)
		{
			LOG.debug("insert(" + user + ")");
			dao.insert(user);		
		}
	...
	}
	
	<decorators>
		<class>org.se.lab.UserDAODecorator</class>
	</decorators>
	
	Run CDITest to execute both decorator and implementation methods.
	
	
How to use Interceptors in CDI applications?	
-------------------------------------------------------------------------------
An interceptor is a class used to interpose in method invocations or lifecycle 
events that occur in an associated target class. 
The interceptor performs tasks, such as logging or auditing, that are separate 
from the business logic of the application and are repeated often within an 
application. 

Example:

	@InterceptorBinding
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.TYPE, ElementType.METHOD})
	public @interface Monitored
	{
	}
	
	@Monitored
	public class UserDAOImpl 
		implements UserDAO
	{
		private final Logger LOG = Logger.getLogger(UserDAOImpl.class);
		
		@Inject
	    private EntityManager em;
		...    	
	}
	
	
	@Interceptor
	@Monitored
	public class MonitorInterceptor 
		implements Serializable
	{
		private static final long serialVersionUID = 1L;
		private final Logger LOG = Logger.getLogger(MonitorInterceptor.class);
	
		@Inject 
		private EntityManager em;
		
		@AroundInvoke
		public Object monitorMethod(InvocationContext ctx) throws Exception
		{
			long before = System.currentTimeMillis();
			
			Object obj = ctx.proceed();
			
			long after = System.currentTimeMillis();
			LOG.debug("Method " + ctx.getMethod().getName() + "() : duration = " + (after - before) + ") : em = " + em);
			return obj;
		}
	}

	<interceptors>
		<class>org.se.lab.MonitorInterceptor</class>
	</interceptors>


	Run CDITest to execute and intercept implementation methods.

Interceptors perform cross-cutting tasks associated with method invocation and 
with the lifecycles of beans, but cannot perform any business logic. 

Decorators, on the other hand, do perform business logic by intercepting business 
methods of beans. 
	
	