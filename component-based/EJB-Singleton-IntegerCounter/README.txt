Creating a Singleton Session Bean
-------------------------------------------------------------------------------
The javax.ejb.Singleton annotation is used to specify that the enterprise bean
implementation class is a singleton session bean.

Initializing Singleton Session Beans
-------------------------------------------------------------------------------
The EJB container is responsible for determining when to initialize a singleton
session bean instance unless the singleton session bean implementation class is
annotated with the javax.ejb.Startup annotation.

The EJB container must initialize the singleton session bean upon application
startup. The singleton session bean is initialized before the EJB container
delivers client requests to any enterprise beans in the application.
This allows the singleton session bean to perform application startup tasks.

Sometimes multiple singleton session beans are used to initialize data for an
application and therefore must be initialized in a specific order. In these cases,
use the javax.ejb.DependsOn annotation to declare the startup dependencies of
the singleton session bean.
The @DependsOn annotation’s value attribute is one or more strings that specify
the name of the target singleton session bean.


Managing Concurrent Access in a Singleton Session Bean
-------------------------------------------------------------------------------
Singleton session beans are designed for concurrent access, situations in which
many clients need to access a single instance of a session bean at the same time.

When creating a singleton session bean, concurrent access to the singleton’s
business methods can be controlled in two ways: container-managed concurrency
and bean-managed concurrency.

The javax.ejb.ConcurrencyManagement annotation is used to specify container-managed
or bean-managed concurrency for the singleton. With @ConcurrencyManagement, a type
attribute must be set to either javax.ejb.ConcurrencyManagementType.CONTAINER or
javax.ejb.ConcurrencyManagementType.BEAN.
If no @ConcurrencyManagement annotation is present on the singleton implementation
class, the EJB container default of container-managed concurrency is used.

Container-Managed Concurrency
-----------------------------
If a singleton uses container-managed concurrency, the EJB container controls
client access to the business methods of the singleton.
The javax.ejb.Lock annotation and a javax.ejb.LockType type are used to specify
the access level of the singleton’s business methods or @Timeout methods.
The LockType enumerated types are READ and WRITE.
> Annotate a singleton’s business or timeout method with @Lock(LockType.READ)
  if the method can be concurrently accessed, or shared, with many clients.
> Annotate the business or timeout method with @Lock(LockType.WRITE) if the
  singleton session bean should be locked to other clients while a client is
  calling that method.
  Typically, the @Lock(LockType.WRITE) annotation is used when clients are
  modifying the state of the singleton.
If no @Lock annotation is present on the singleton class, the default lock type,
@Lock(LockType.WRITE), is applied to all business and timeout methods.

Example:

    @ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)  // default
    @Singleton
    public class ExampleSingletonBean
    {
      private String state;

      @Lock(LockType.READ)
      public String getState()
      {
        return state;
      }

      @Lock(LockType.WRITE)  // default
      public void setState(String newState)
      {
        state = newState;
      }
    }

The @AccessTimeout annotation can be applied to both @Lock(LockType.READ) and
@Lock(LockType.WRITE) methods.


Bean-Managed Concurrency
------------------------
Singletons that use bean-managed concurrency allow full concurrent access to
all the business and timeout methods in the singleton. The developer of the
singleton is responsible for ensuring that the state of the singleton is
synchronized across all clients.

Developers who create singletons with bean-managed concurrency are allowed to
use the Java programming language synchronization primitives, such as
synchronization and volatile, to prevent errors during concurrent access.

