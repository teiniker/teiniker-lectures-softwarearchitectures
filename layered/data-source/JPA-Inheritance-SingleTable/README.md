# JPA Inheritance 


## Introduction

In object-oriented programming, inheritance allows classes to form hierarchies 
where subclasses inherit properties and methods from parent classes. However, 
relational databases do not have native support for inheritance. JPA provides 
three strategies to map object hierarchies to relational tables:

1. **Single Table Strategy**: All classes in the hierarchy use a single table 
    with a discriminator column.

2. **Table per Class Strategy**: Each class (including abstract parent) gets 
    its own table with duplicated columns.

3. **Joined Strategy**: Each class gets its own table, with foreign keys joining 
    child tables to parent tables

Each strategy has different performance and storage characteristics.


## Single Table Strategy

### Annotations in the Source Code

In the **Person** class (parent):

```java
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DISCRIMINATOR", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("PERSON")
public class Person { ... }
```

In the **Customer** class (child):

```java
@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends Person { ... }
```

In the **Employee** class (child):

```java
@Entity
@DiscriminatorValue("EMPLOYEE")
public class Employee extends Person { ... }
```

- `@Inheritance(strategy=InheritanceType.SINGLE_TABLE)`: On the parent class, 
    specifies that all classes in the hierarchy share a single table.
- `@DiscriminatorColumn(name="DISCRIMINATOR", discriminatorType=DiscriminatorType.STRING)`: 
    On the parent class, defines a column used to distinguish between entity 
    types. The discriminator type can be `STRING`, `CHAR`, or `INTEGER`.
- `@DiscriminatorValue("PERSON")`: On the parent class, specifies the 
    discriminator value for instances of Person (e.g., a row where 
    `DISCRIMINATOR='PERSON'`).
- `@DiscriminatorValue("CUSTOMER")` and `@DiscriminatorValue("EMPLOYEE")`: 
    On child classes, specify the discriminator values for `Customer` 
    and `Employee` instances.

### Generated SQL Schema

```sql
create table PERSON (
    DISCRIMINATOR varchar2(31 char) not null,
    id number(10,0) not null,
    firstName varchar2(255 char),
    lastName varchar2(255 char),
    city varchar2(255 char),
    state varchar2(255 char),
    street varchar2(255 char),
    employeeId number(10,0),
    primary key (id)
);

create sequence hibernate_sequence;
```

- A single table `PERSON` contains all rows for `Person`, `Customer`, and 
    `Employee` entities.
- The `DISCRIMINATOR` column stores the entity type (`PERSON`, `CUSTOMER`, 
    or `EMPLOYEE`).
- All columns from all entity classes are present in the table, including 
    `city`, `street`, `state` (`Customer`) and `employeeId` (`Employee`).
- Unused columns are NULL for entities that don't have that property 
    (e.g., a `Person` row has NULL in `city`, `street`, `state`, `employeeId`).

### Consequences

**Pros:**
- Simple schema with a single table.
- Fast queries: No JOINs needed to retrieve data.
- Efficient for querying polymorphic types (all employees, all customers, etc.).

**Cons:**
- Table becomes wide with many NULL columns for attributes not relevant to 
    all entity types.
- Limited data validation at the database level: `NULL` constraints cannot 
    be enforced for subclass-specific columns.
- Not suitable for large hierarchies or when subclasses have many unique 
    attributes.




*Egon Teiniker, 2016-2026, GPL v3.0*