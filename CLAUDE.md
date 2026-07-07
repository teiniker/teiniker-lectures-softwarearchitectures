# CLAUDE.md

## Repository Overview

This is a **lecture repository** (`org.se.lab`) demonstrating software architecture patterns through self-contained Java examples. 

## Build & Test Commands

Each example must be built from its own directory:

```bash
# Build and run all tests for one example
cd layered/data-source/DAO-JDBC-User
mvn clean test

# Run a single test class
mvn test -Dtest=UserDAOTest

# Run a single test method
mvn test -Dtest=UserDAOTest#testFindAll

# Start a Spring Boot application
mvn spring-boot:run

# Package a WAR for WildFly deployment
mvn clean package
```

Parent POMs at `layered/data-source/pom.xml`, `layered/domain/pom.xml`, `services/pom.xml`, and `software-quality/pom.xml` can build all child modules in that section via `mvn clean test` from the parent directory.

## Java Versions

Java 25 

## Key Conventions

- All source packages follow `org.se.lab.*`
- Tests use JUnit 4 (`junit:junit:4.13.2`) throughout, not JUnit 5
- Logging uses Log4j 1.x with `log4j.properties` in `src/test/resources/`
- Projects with `*-Exercise` suffix are intentionally incomplete for student work
- `ClassDiagram.png` files document the design visually alongside the code

### Coding Standard

- **Line width**: Maximum **80 characters** per line
- **Indentation**: **4 spaces** per level (not tabs)
- **Braces**: 
  - Always surround blocks with braces `{ }`, even for single statements
  - Left brace `{` goes on the next line after the statement
  - Right brace `}` aligns with the opening keyword
- **Spacing**:
  - Binary operators (`+`, `-`, `*`, `/`, `==`, etc.) surrounded by spaces
  - Unary operators (`++`, `--`, `!`, `~`) have no space on operand side
  - No spaces around member operators (`[`, `]`)
- **Variable declarations**: First characters aligned within a block
- **Blank lines**: Before and after natural blocks of code (loops, if/else, declarations)

#### Example

```java
package org.se.lab;

public class Book
{
	/*
	 * Constructor
	 */
	public Book(String isbn, String author, String title)
	{
		this.isbn = isbn;
		this.author = author;
		this.title = title;
	}

	/*
	 * Property: isbn:String
	 */
	private String isbn;
	public String getIsbn()
	{
		return isbn;
	}
	public void setIsbn(String isbn)
	{
		this.isbn = isbn;
	}

	/*
	 * Property: author:String
	 */
	private String author;
	public String getAuthor()
	{
		return author;
	}
	public void setAuthor(String author)
	{
		this.author = author;
	}

	/*
	 * Property: title:String
	 */
	private String title;
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}

	public String toJson()
	{
		return "{" +
				"\"isbn\":\"" + isbn + "\"," +
				"\"author\":\"" + author.replace("\"", "\\\"") + "\"," +
				"\"title\":\"" + title.replace("\"", "\\\"") + "\"" +
				"}";
	}

	/*
	 * Object methods
	 */

	@Override
	public String toString()
	{
		return "Book{" +
				"isbn='" + isbn + '\'' +
				", author='" + author +
				"', title='" + title +
				"'}";
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Book book = (Book) o;
        return isbn.equals(book.isbn);
    }

	@Override
	public int hashCode()
	{
		return isbn.hashCode();
	}
}
```


### Documentation 

* Use only 80 chars per line for documentation text.
* Don't use — and emojis in generated text.
