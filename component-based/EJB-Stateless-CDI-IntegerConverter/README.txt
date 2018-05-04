Contexts and Dependency Injection (CDI)
-------------------------------------------------------------------------------


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

	
	
How to use Decorators in CDI applications?
-------------------------------------------------------------------------------
A decorator is a Java class that is annotated javax.decorator.Decorator and 
that has a corresponding decorators element in the beans.xml file.

A decorator bean class must also have a delegate injection point, which is 
annotated javax.decorator.Delegate. This injection point can be a field, a 
constructor parameter, or an initializer method parameter of the decorator 
class.

