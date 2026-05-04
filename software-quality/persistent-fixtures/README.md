# Persistent Test Fixtures 

From a technical point of view, we can use the **same testing framework** (and the **same xUnit patterns**) for acceptance tests as for unit tests.

Particular attention must be paid to the **setup** and **teardown** phase when we deal with **Persistent Test Fixtures**. For performance reasons, we are often forced to work with **Shared Fixtures**.

Components that frequently appear in integration tests are:

* **Filesystem**: Data is stored and passed on in various file formats.

* **Databases**: Databases are a particularly useful form of data 
    storage. They allow not only the storage but also the simple 
    evaluation of data via query languages.

    - MariaDB
        - [Introduction](MariaDB/README.md)
        - [JDBC-UserTable](JDBC-UserTable/)

    - SQLite
        - [Introduction](SQLite/README.md)
        - [SQLite-User](SQLite-User/)
        - [SQLite-UserTable](SQLite-UserTable/)
        - [SQLite-UserTable-InMemory](SQLite-UserTable-InMemory/)

    
## References

* Glenford J. Myers, Corey Sandler, Tom Badgett. **The Art of Software Testing**. Wiley, 3rd Edition 2012

* Lisa Crispin, Janet Gregory. **Agile Testing**. Addison Wesley, 2009

*Egon Teiniker, 2019-2026, GPL v3.0*