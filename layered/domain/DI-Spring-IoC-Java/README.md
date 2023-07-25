# Spring Bean Configuration

We need to use the `@Configuration` annotation to indicate that the class 
contains Spring bean configurations and the appropriate annotations for 
defining beans. 

```Java
@Configuration
public class AppConfiguration 
{
    @Bean
    public PersonDAO personDAO() 
    {
        return new PersonDAOInMemoryImpl();
    }

    @Bean
    public PersonDAOLogger personDAOlogger(PersonDAO personDAO) 
    {
        PersonDAOLogger personDAOLogger = new PersonDAOLogger();
        personDAOLogger.setDao(personDAO);
        return personDAOLogger;
    }
    // ...
}
```

In the Java configuration class, we use the `@Configuration` annotation 
to indicate that this class contains bean definitions. 

The `personDAO()` method is annotated with `@Bean`, and it creates a new 
instance of `PersonDAOInMemoryImpl` which will be registered as a bean with 
the name `personDAO`.

Similarly, the `personDAOlogger()` method is also annotated with `@Bean`, 
and it takes an argument of `PersonDAO`. This method creates a new instance 
of `PersonDAOLogger` and sets the `PersonDAO` bean (obtained via dependency 
injection) as its `dao` property.

Finally, we need to use the `AnnotationConfigApplicationContext` to load 
this configuration class and obtain the beans.

```Java
public class MainApplication 
{
    public static void main(String[] args) 
    {
        // Load the configuration class
        AnnotationConfigApplicationContext context = 
                new AnnotationConfigApplicationContext(MyAppConfig.class);

        // Get the beans
        PersonViewHelper helper = context.getBean(PersonViewHelper.class);

        // Now you can use the beans as needed
        // ...

        // Close the context when done
        context.close();
    }
}
```

This example code will load the configuration class and create and wire
the beans for you to use in your application.

## References
* [Baeldung: The Spring ApplicationContext](https://www.baeldung.com/spring-application-context)

*Egon Teiniker, 2019-2023, GPL v3.0*