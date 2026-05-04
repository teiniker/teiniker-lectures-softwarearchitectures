# Example: SpringBoot Integration Tests

Using Spring Boot, we can easily implement integration tests for web services and database access.

An integration test actually starts up our application (often on a random port), sends real HTTP requests to the endpoints, and verifies that everything works together—from the REST API layer down to the database.

Spring Boot provides the `@SpringBootTest` annotation, which loads the full application context for testing. We can use `TestRestTemplate` or `WebTestClient` to interact with your REST endpoints.

_Example:_
```Java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiTest
{
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookRepository bookRepository;

    // A test book instance to be used for retrieval
    private Book testBook;

    @BeforeEach
    public void setup()
    {
        // Ensure a clean slate for every test run
        bookRepository.deleteAll();

        // {"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"}
        // Create and save a test Book instance
        testBook = new Book();
        testBook.setId(1);
        testBook.setAuthor("Joshua Bloch");
        testBook.setTitle("Effective Java");
        testBook.setIsbn("978-0134685991");
        bookRepository.save(testBook);
    }

    @AfterEach
    public void cleanup()
    {
        bookRepository.deleteAll();
    }

    //...
}
```

* `@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)`
    - Tells Spring Boot to start the full application context and run an embedded server 
    on a random port.
    - So our REST endpoints are actually available for testing—just like they would be 
    in production.

* `@LocalServerPort`
    - Injects the random port number that the embedded server is running on.

* `@Autowired TestRestTemplate`
    - A special RestTemplate designed for integration tests. Lets you easily perform 
    HTTP requests to your running server.

* `@Autowired BookRepository`
    - Lets you interact with the real database (usually H2 or similar for tests), to 
    set up and clean up test data.

### Test Setup and Teardown

* @BeforeEach public void setup(): Make sure every test runs in a clean state with a 
    known database setup.
    - bookRepository.deleteAll(); — Clear all books from the test DB.
    - Create a new Book object (testBook) with preset values (id, author, title, isbn).
    - Save it to the DB (bookRepository.save(testBook);).

* @AfterEach public void cleanup(): Clean up after each test to prevent test data from 
    leaking between tests.
    - Delete all books.

### Test Case

This test sends a GET request to the REST API to retrieve a specific Book object by its ID. 
It verifies that the response status is 200 OK and checks that the returned Book details 
match the expected values.

```Java
    @Test
    public void testFindBook()
    {
        // Build the URL to the find endpoint
        String url = "/api/books/" + testBook.getId();

        // Perform a GET request to retrieve the book
        ResponseEntity<Book> response = restTemplate.getForEntity(url, Book.class);

        // Verify the HTTP status code is 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify the response body contains the expected book details
        Book foundBook = response.getBody();
        assertNotNull(foundBook);
        assertEquals("Effective Java", foundBook.getTitle());
        assertEquals("Joshua Bloch", foundBook.getAuthor());
        assertEquals("978-0134685991", foundBook.getIsbn());
    }
```

* Build the endpoint URL: `String url = "/api/books/" + testBook.getId();`

* Send HTTP GET request: `restTemplate.getForEntity(url, Book.class);`
    - Calls the real API endpoint as an external client would.

* Assertions:
    - Check HTTP status: Makes sure the API returned `200 OK`.
    - Check response body: Ensure the returned book matches what we 
        saved in `setup()`.


*Egon Teiniker, 2016-2025, GPL v3.0*
