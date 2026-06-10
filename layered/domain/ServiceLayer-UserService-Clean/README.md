# Example: Clean Architecture - User Service

## Clean Architecture

Clean Architecture organizes code into concentric layers where **source code
dependencies can only point inward** - toward higher-level policy and away from
lower-level details.

The two central rules are:

* **The Dependency Rule**: Inner layers define interfaces and entities; outer
  layers implement those interfaces. Nothing in an inner layer may name
  anything from an outer layer.

* **Dependency Inversion Principle (DIP)**: When control must flow outward
  (e.g., the service calls the database), the dependency is inverted by placing
  an interface in the inner layer that the outer layer implements.

The main benefits are:

* **Framework independence**: the domain model has no dependency on any
  framework or library. Frameworks are details that live in the outermost layer.

* **Testability**: business rules can be tested without a database, web server,
  or any other external system.

* **Replaceability**: the database, UI, or any external service can be swapped
  out without touching the business layer.


## Implementation

The example contains two layers with a strict one-way dependency:

```
org.se.lab.business   (inner circle - domain + use cases)
org.se.lab.data       (outer circle - JDBC infrastructure)
```

### Business layer (`org.se.lab.business`)

This layer owns everything the domain needs and nothing else.

| Class | Role |
|---|---|
| `User` | Entity. Combines state and behavior: `setPassword()` always stores a SHA-512 hash; `User.restore()` is the reconstitution path for the DAO. |
| `UserDTO` | Data Transfer Object returned by `UserService`. Declared as a `record`, which enforces immutability and generates `toString()`, `equals()`, and `hashCode()` from its four components: `id`, `firstname`, `lastname`, and `username`. The `password` field is intentionally omitted. |
| `UserDAO` | Repository Port (interface). Defines what the domain needs from persistence without knowing how it is implemented. |
| `RepositoryException` | Unchecked exception that is part of the port contract. |
| `UserService` | Use-case interface visible to the presentation layer. Returns `UserDTO`, not `User`. |
| `AbstractService` | Base class managing the JDBC `Connection` and transaction helpers. |
| `UserServiceImpl` | Concrete use case. Delegates persistence to `UserDAO`, maps `User` entities to `UserDTO`, and wraps each operation in a transaction. |
| `ServiceFactory` | Wires the full service stack. Uses `DataFactory` to obtain a `UserDAO` without knowing the concrete class. |
| `ServiceException` | Unchecked exception translated from `RepositoryException` so data-layer details never reach the caller. |

The `UserDTO` is the **boundary type** of the use-case interface: the `User`
entity never crosses the boundary into the presentation layer. `UserServiceImpl`
maps entities to DTOs after the transaction commits. The mapping lives in the
service -- `UserDTO` has no knowledge of `User` and carries no converter methods.

Using a `record` for `UserDTO` makes its architectural role visible in the type
declaration itself: a record is by definition a transparent, immutable data
carrier with no identity and no mutable state, which is exactly what a DTO is.

The business layer has **one controlled import from the outer layer**: `DataFactory`.
No class in `org.se.lab.business` imports `UserDAOImpl`, `AbstractDAOImpl`, or any
JDBC type.

### Data layer (`org.se.lab.data`)

This layer depends on the business layer, not the other way around.

| Class | Role |
|---|---|
| `AbstractDAOImpl` | Base class holding the `Connection` and JDBC resource cleanup helpers. |
| `UserDAOImpl` | Concrete JDBC implementation of `org.se.lab.business.UserDAO`. Throws `RepositoryException` on failure. Uses `User.restore()` when reconstructing rows read from the database. |
| `DataFactory` | Public entry point of the data layer. Creates and returns a `UserDAO`. Hides `UserDAOImpl` from the caller. |


### Dependency Inversion: `UserDAO`

In the original layered design, the business layer imports from the data layer:

```
org.se.lab.business.UserServiceImpl
    imports org.se.lab.data.UserDAO        // interface defined in data
    imports org.se.lab.data.User           // entity defined in data
    imports org.se.lab.data.DAOException   // exception defined in data
```

After applying the Dependency Inversion Principle, `UserDAO` and `User` are
moved into the business layer. The data layer now imports from the business
layer to implement the interface:

```
org.se.lab.data.UserDAOImpl
    implements org.se.lab.business.UserDAO   // interface defined in business
    uses       org.se.lab.business.User      // entity defined in business
    throws     org.se.lab.business.RepositoryException
```

The control flow still goes from service to DAO (inward to outward), but the
source code dependency is inverted: `data` depends on `business`, never the
reverse.


## Comparison with `ServiceLayer-UserService`

| Concern | ServiceLayer-UserService | ServiceLayer-UserService-Clean |
|---|---|---|
| Where is `User` defined? | `org.se.lab.data` | `org.se.lab.business` |
| Where is `UserDAO` defined? | `org.se.lab.data` | `org.se.lab.business` |
| Dependency direction | `business` -> `data` | `data` -> `business` |
| Exception translation | `DAOException` leaks into service | `RepositoryException` defined in business |
| Password hashing | `PasswordEncoder` utility called by service | `User.setPassword()` hashes automatically |
| DAO wiring | `ServiceFactory` in `business` creates `UserDAOImpl` directly | `DataFactory` in `data` hides the concrete class |
| Boundary type | `User` entity returned to caller | `UserDTO` returned; `password` not exposed |

The original example follows a classic layered architecture where dependencies
flow top-down: the business layer calls into the data layer and therefore depends
on it. This is simple and common, but it means the domain model is owned by the
infrastructure layer.

The clean version inverts that relationship. The domain model is self-contained
and can be compiled, tested, and reasoned about without any knowledge of JDBC,
MySQL, or any other infrastructure detail.


*Egon Teiniker, 2016-2026, GPL v3.0*

