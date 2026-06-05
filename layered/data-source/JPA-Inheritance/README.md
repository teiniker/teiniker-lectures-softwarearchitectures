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


## Joined Strategy

### Annotations in the Source Code

In the **Person** class (parent):

```java
@Inheritance(strategy=InheritanceType.JOINED)
public class Person { ... }
```

In the **Customer** class (child):

```java
@Entity
@Table(name=Customer.TABLE_NAME)
@PrimaryKeyJoinColumn(name="id")
public class Customer extends Person { ... }
```

In the **Employee** class (child):

```java
@Entity
@Table(name=Employee.TABLE_NAME)
@PrimaryKeyJoinColumn(name="id")
public class Employee extends Person { ... }
```

- `@Inheritance(strategy=InheritanceType.JOINED)`: On the parent class, 
    specifies that each class (parent and child) gets its own table, 
    with child tables linked via foreign keys.
- `@PrimaryKeyJoinColumn(name="id")`: On each child class, specifies 
    that the primary key column in the child table also serves as a 
    foreign key referencing the parent table.
- No discriminator column is needed; the foreign key structure implicitly 
    defines the entity type.

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
    city varchar2(255 char),
    state varchar2(255 char),
    street varchar2(255 char),
    primary key (id),
    constraint FK_CUSTOMER_PERSON foreign key (id) references PERSON(id)
);

create table EMPLOYEE (
    id number(10,0) not null,
    employeeId number(10,0) not null,
    primary key (id),
    constraint FK_EMPLOYEE_PERSON foreign key (id) references PERSON(id)
);

create sequence hibernate_sequence;
```

- Three separate tables: `PERSON` (parent), `CUSTOMER` (child), `EMPLOYEE` (child).
- `PERSON` contains the shared properties (`id`, `firstName`, `lastName`).
- `CUSTOMER` contains only `Customer`-specific columns (`city`, `street`, 
    `state`), plus `id` as a foreign key to `PERSON`.
- `EMPLOYEE` contains only `Employee`-specific columns (`employeeId`), plus `id` 
    as a foreign key to `PERSON`.
- Child tables use the same `id` value as their corresponding row in the parent 
    table, establishing a one-to-one relationship.
- To retrieve a `Customer` with all its properties, a `JOIN` between `CUSTOMER` 
    and `PERSON` is required.

### Consequences

**Pros:**
- Normalized schema: No column duplication; each column appears in exactly 
    one table.
- Strong database constraints: Foreign keys ensure referential integrity 
    between parent and child.
- Flexible: Easy to add new subclasses without affecting existing tables.
- Efficient for subclass-specific queries (retrieve only `Customer` data 
    without JOINs).

**Cons:**
- More complex queries: Retrieving polymorphic types requires JOINs across 
    multiple tables.
- Performance overhead: JOINs add computational cost for queries spanning 
    parent and child tables.
- More tables to manage: Increases schema complexity.
- Slower INSERT/UPDATE operations: May require operations on multiple tables.


*Egon Teiniker, 2016-2026, GPL v3.0*