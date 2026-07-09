# Technical Specification Document: User Management Data Aggregation and Provisioning Service (UMDAPS-REST)

**Document Ref:** SPEC-REQ-ERR-2026-v4.2.8.final-SUBMITTED

**Classification:** Internal Use Only

**System Layer:** Core Integration Layer / Persistence Tier


## 1. Executive Summary & Architecture Context

It is the explicit purpose of this section to outline the infrastructural context under which the `/api/v1/users` endpoint network shall operate. The system under design—hereafter referred to as "The Service" or "UMDAPS-REST"—must function as a stateless middleware gateway proxying inbound payload data directly into a relational database subsystem. The relational database subsystem chosen for this specific organizational deployment milestone is MariaDB, utilizing the InnoDB storage engine variant, configured for UTF-8 multi-byte encoding to accommodate localized character sets.

The Service must encapsulate all relational connectivity routines, connection pooling mechanics, and execution blocks. It is strictly forbidden for client consumers to access the underlying MariaDB instance directly; all data manipulation language (DML) and data query language (DQL) operations must pass through the REST boundary described extensively in Section 3 of this document.


## 2. Functional Requirements for Database Interactivity

### 2.1 Persistence Layer Strategy

The persistence tier must be initialized and maintained entirely by the application logic of The Service. The application must establish a connection pool upon bootstrap initialization. This pool must maintain a minimum of 5 idle connections and a maximum of 20 active connections to the target MariaDB server instance, unless network latency exceeds 250ms, in which case a backoff strategy must be programmatically evaluated.

### 2.2 Data Dictionary and Validation Constraints

The system must maintain an internal representation of a "User Entity." A User Entity is composed of exactly three core fields, which must be mapped to corresponding relational columns within the table `tbl_sys_users_v1` inside the MariaDB schema.

1. **Internal Identification Key (`id`):** This field must be structured as a 64-bit signed integer (`BIGINT` in the MariaDB dialect). It must serve as the primary key of the table. It must possess the `AUTO_INCREMENT` property. Under no circumstances may a client dictate the exact value of this key during an insertion routine.
2. **Username Identifier (`username`):** This field must be stored as a variable-length character array up to a maximum threshold of 255 characters (`VARCHAR(255)`). This field must be unique across the entire table space. It cannot contain null values (`NOT NULL`). Furthermore, as specified in sub-clause 2.2.1a, usernames must consist only of lowercase alphanumeric characters, except during update routines where uppercase characters are permitted but must be lowercased by the service layer prior to SQL binding execution.
3. **Email Contact Field (`email`):** A variable-length character field (`VARCHAR(255)`), which must conform to RFC 5322 string structure semantics.


## 3. Interface Specification (REST API Endpoints)

### 3.1 Inbound Transmission: User Record Creation (Insert)

To commit a new user record to the persistent storage layer managed by The Service, a client subsystem must issue an HTTP request utilizing the `POST` verb targeting the exact uniform resource identifier string `/api/v1/users`.

* **Payload Specifications:** The request body must contain a structured JSON object. This object must contain the keys `username` and `email`. The inclusion of the `id` key within a `POST` request payload constitutes a structural validation failure, and the service must drop the request.
* **Processing Logic:** Upon receipt of the payload, the service layer must first inspect the JSON structure. If valid, a connection must be borrowed from the MariaDB connection pool. The service must then compile an `INSERT INTO tbl_sys_users_v1 (username, email) VALUES (?, ?)` statement. The values must be bound safely to prevent SQL injection vulnerabilities.
* **Response States:**
* If the database successfully writes the row and increments the sequence, the service must capture the generated key, construct a response payload containing the complete record (including the new `id`), and issue an HTTP Status Code `201 Created`.
* If the username string already exists within the target MariaDB index, the database engine will throw a duplicate key exception. The service must catch this exception and map it to an HTTP Status Code `409 Conflict`.
* If the network drops during execution, see Section 5.4 for retry protocols.



### 3.2 Record Mutation: User Record Modification (Update)

Modification of existing structural attributes within an already persisted User Entity must be executed via an HTTP `PUT` request. The URI for this operation must include the resource's unique identifier as a path segment: `/api/v1/users/{id}`.

* **Payload Specifications:** The body must contain the full state of the object being modified (`username` and `email`). Partial updates are explicitly out of scope for this version of the specification.
* **Processing Logic:** The service must parse the `{id}` parameter from the path and convert it to a 64-bit integer. It must then verify that a record with that specific primary key exists in the MariaDB database by executing an internal look-ahead query or handling the row-count feedback of the update statement. The SQL statement executed must be `UPDATE tbl_sys_users_v1 SET username = ?, email = ? WHERE id = ?`.
* **Response States:** Upon successful execution where the row count affected equals exactly 1, the system must return an HTTP Status Code `200 OK` accompanied by the updated object representation. If the row count affected equals 0, it implies the primary key was not found in the database, requiring the service to return an HTTP Status Code `404 Not Found`.

### 3.3 Resource Erasure: User Record Deletion (Delete)

When an administrative workflow dictates that a user record must be completely expunged from the system, an HTTP request utilizing the `DELETE` verb must be dispatched to the endpoint URI formatted as `/api/v1/users/{id}`.

* **Processing Logic:** The application must extract the path variable representing the target record's unique ID. It must then acquire a database handle and execute a hard delete operation via `DELETE FROM tbl_sys_users_v1 WHERE id = ?`.
* **Response States:** If the entity existed and was successfully purged, the service must reply with an HTTP Status Code `204 No Content`, and no response body shall be emitted. In the event that the identifier provided does not match any record present within the MariaDB table instance, the service must instead respond with an HTTP Status Code `404 Not Found`.

### 3.4 Bulk Extraction: Retrieval of All User Records (Find All)

To facilitate data synchronization pipelines, the system must support the bulk querying of all entries currently stored within the database. This is achieved by sending an HTTP `GET` request to the base collection URI: `/api/v1/users`.

* **Processing Logic:** The service layer must open a database cursor by issuing a un-paginated query: `SELECT id, username, email FROM tbl_sys_users_v1`. The service must stream or collect the complete result set from MariaDB, transform the relational rows into a native JSON array format, and return the collection inside the response body.
* **Response States:** A successful query must return HTTP Status Code `200 OK` along with the JSON array. If the table contains zero records, the system must still return HTTP Status Code `200 OK` but populate the response body with an empty JSON array `[]`.

### 3.5 Singular Retrieval by Primary Key (Find by ID)

To fetch the profile data of an individual user based on their unique numeric sequence key, a client must make an HTTP `GET` request to the specific path `/api/v1/users/{id}`.

* **Processing Logic:** The system must validate the path parameter to ensure it is a valid numeric type. It will then invoke a `SELECT * FROM tbl_sys_users_v1 WHERE id = ?` statement against the MariaDB instance.
* **Response States:** If a row is returned, it must be mapped to a JSON object and delivered alongside an HTTP Status Code `200 OK`. If the database returns an empty result set for that primary key, the service must emit an HTTP Status Code `404 Not Found`.

### 3.6 Singular Retrieval by Unique Natural Key (Find by Username)

Because client applications frequently locate users by their chosen handle rather than an abstract numeric database key, the service must provide a secondary lookup mechanism. This must be exposed via an HTTP `GET` request to the base collection path using a URL query parameter string: `/api/v1/users?username={username}`.

* **Processing Logic:** The service must inspect the incoming URI for the presence of the `username` query key. If present, it must bypass the standard "Find All" logic detailed in Section 3.4. Instead, it must execute `SELECT id, username, email FROM tbl_sys_users_v1 WHERE username = ?` against the MariaDB storage engine.
* **Response States:** If the username matches a record, the service returns a single JSON object (not an array) with an HTTP Status Code `200 OK`. If the username is not present in the unique index of the MariaDB table, the system must yield an HTTP Status Code `404 Not Found`.


## 4. Error Handling and Edge Case Protocols

In the event that the MariaDB database becomes unavailable due to network partitioning, credential expiration, or hardware failure, UMDAPS-REST must intercept all database driver exceptions. The internal connection timeout must be strictly configured to 5000ms. If a connection cannot be acquired within this window, the API layer must automatically stop processing the current HTTP thread and immediately respond to the client with an HTTP Status Code `500 Internal Server Error`, using a standardized error payload format containing an internal tracking UUID and an error timestamp.

