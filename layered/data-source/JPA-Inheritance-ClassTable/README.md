# JPA Inheritance 

## Table per Class Strategy

### Annotations in the Source Code

In the **Person** class (parent):

```java
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Person { ... }
```

In the **Customer** class (child):

```java
@Entity
@Table(name=Customer.TABLE_NAME)
public class Customer extends Person { ... }
```

In the **Employee** class (child):

```java
@Entity
@Table(name=Employee.TABLE_NAME)
public class Employee extends Person { ... }
```

- `@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)`: On the parent 
    class, specifies that each concrete class in the hierarchy gets its 
    own table.
- `@Table(name=...)`: On each child class, specifies the table name for 
    that entity class.
- No discriminator column is needed; each class has its own table, making 
    the type implicit.

### Generated SQL Schema

```sql
create table PERSON (
    id number(10,0) not null,
    firstName varchar2(255 char),
    lastName varchar2(255 char),
    primary key (id)
);

create table CUSTOMER (
    id number(10,0) not null,
    firstName varchar2(255 char),
    lastName varchar2(255 char),
    city varchar2(255 char),
    state varchar2(255 char),
    street varchar2(255 char),
    primary key (id)
);

create table EMPLOYEE (
    id number(10,0) not null,
    firstName varchar2(255 char),
    lastName varchar2(255 char),
    employeeId number(10,0) not null,
    primary key (id)
);

create sequence hibernate_sequence;
```

- Each class (`Person`, `Customer`, `Employee`) has its own table.
- Child tables duplicate parent columns (`firstName`, `lastName`, 
    `id`) rather than using foreign keys.
- `CUSTOMER` includes all `Person` columns plus `Customer`-specific 
    columns (`city`, `street`, `state`).
- `EMPLOYEE` includes all `Person` columns plus `Employee`-specific 
    columns (`employeeId`).
- Each table has its own primary key; no foreign key relationships 
    between parent and child tables.

### Consequences

**Pros:**
- Normalized per entity: Each table only contains relevant columns.
- Better data validation: Constraints can be properly enforced on 
    subclass-specific columns.
- Flexible: Easy to add new subclasses with unique attributes.

**Cons:**
- Data duplication: Parent columns are repeated in every child table.
- Complex queries: Querying polymorphic types (e.g., all `Person` 
    instances) requires `UNION` queries across multiple tables.
- Less efficient for polymorphic queries and joins.
- `INSERT`/`UPDATE` may require changes to multiple tables.


*Egon Teiniker, 2016-2026, GPL v3.0*