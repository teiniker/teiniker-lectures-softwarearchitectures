# **Epic: User Management Data Aggregation and Provisioning Service (UMDAPS-REST)**

All examples assume the service is reachable at `http://localhost:8080`.

## **User Story 1: Create a New User**

**As an** API client

**I want to** submit a username and email to the user collection endpoint

**So that** I can provision a new unique user account in the system

* **Acceptance Criteria:**
* **Given** a valid payload containing a unique lowercase alphanumeric `username` and an `email` conforming to RFC 5322
* **When** a `POST` request is sent to `/api/v1/users`
* **Then** the system should create the record and return HTTP `201 Created` with the full user object, including the auto-generated `id`
```bash
curl -i -X POST http://localhost:8080/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{"username": "jdoe", "email": "jdoe@example.com"}'
# Expected: HTTP/1.1 201 Created
# {"id": 1, "username": "jdoe", "email": "jdoe@example.com"}
```
* **Given** a payload whose `username` already exists in the MariaDB database
* **When** a `POST` request is sent to `/api/v1/users`
* **Then** the system should return HTTP `409 Conflict`
```bash
curl -i -X POST http://localhost:8080/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{"username": "jdoe", "email": "another@example.com"}'
# Expected: HTTP/1.1 409 Conflict
```
* **Given** a payload that contains an explicit `id` key
* **When** a `POST` request is sent to `/api/v1/users`
* **Then** the system should treat it as a structural validation failure and drop the request
```bash
curl -i -X POST http://localhost:8080/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{"id": 42, "username": "jdoe", "email": "jdoe@example.com"}'
# Expected: HTTP/1.1 400 Bad Request
```


## **User Story 2: Update an Existing User**

**As an** API client

**I want to** submit the full username and email for a specific user ID

**So that** I can keep that user's account details accurate and up to date

* **Acceptance Criteria:**
* **Given** a user ID that exists in the database and a full payload (`username` and `email`)
* **When** a `PUT` request is sent to `/api/v1/users/{id}`
* **Then** the system should update the record in MariaDB and return HTTP `200 OK` with the updated object
```bash
curl -i -X PUT http://localhost:8080/api/v1/users/1 \
  -H "Content-Type: application/json" \
  -d '{"username": "jdoe", "email": "jdoe.new@example.com"}'
# Expected: HTTP/1.1 200 OK
# {"id": 1, "username": "jdoe", "email": "jdoe.new@example.com"}
```
* **Given** a payload containing uppercase characters in the `username` field
* **When** a `PUT` request is processed
* **Then** the system must lowercase the username before it is bound to the SQL statement and persisted
```bash
curl -i -X PUT http://localhost:8080/api/v1/users/1 \
  -H "Content-Type: application/json" \
  -d '{"username": "JDoe", "email": "jdoe@example.com"}'
# Expected: HTTP/1.1 200 OK
# {"id": 1, "username": "jdoe", "email": "jdoe@example.com"}
```
* **Given** a user ID that does not exist in the database
* **When** a `PUT` request is sent to `/api/v1/users/{id}`
* **Then** the system should return HTTP `404 Not Found`
```bash
curl -i -X PUT http://localhost:8080/api/v1/users/9999 \
  -H "Content-Type: application/json" \
  -d '{"username": "jdoe", "email": "jdoe@example.com"}'
# Expected: HTTP/1.1 404 Not Found
```


## **User Story 3: Delete a User**

**As an** administrator

**I want to** request the removal of a specific user ID

**So that** I can permanently purge unneeded or obsolete user accounts

* **Acceptance Criteria:**
* **Given** a user ID that exists in the database
* **When** a `DELETE` request is sent to `/api/v1/users/{id}`
* **Then** the system should remove the row from MariaDB and return HTTP `204 No Content` with no response body
```bash
curl -i -X DELETE http://localhost:8080/api/v1/users/1
# Expected: HTTP/1.1 204 No Content
```
* **Given** a user ID that does not exist in the database
* **When** a `DELETE` request is sent to `/api/v1/users/{id}`
* **Then** the system should return HTTP `404 Not Found`
```bash
curl -i -X DELETE http://localhost:8080/api/v1/users/9999
# Expected: HTTP/1.1 404 Not Found
```


## **User Story 4: Retrieve All Users**

**As an** API client operating a data synchronization pipeline

**I want to** request a list of all registered users

**So that** I can synchronize or audit the full set of accounts across subsystems

* **Acceptance Criteria:**
* **Given** one or more user records exist in the database
* **When** a `GET` request is sent to `/api/v1/users`
* **Then** the system should return HTTP `200 OK` with a JSON array containing all user records
```bash
curl -i -X GET http://localhost:8080/api/v1/users
# Expected: HTTP/1.1 200 OK
# [{"id": 1, "username": "jdoe", "email": "jdoe@example.com"}, ...]
```
* **Given** zero user records exist in the database
* **When** a `GET` request is sent to `/api/v1/users`
* **Then** the system should return HTTP `200 OK` with an empty JSON array `[]`
```bash
curl -i -X GET http://localhost:8080/api/v1/users
# Expected: HTTP/1.1 200 OK
# []
```


## **User Story 5: Find a User by ID**

**As an** API client

**I want to** look up a specific user using their unique numeric ID

**So that** I can display or process details for an individual account

* **Acceptance Criteria:**
* **Given** a user ID that exists in the database
* **When** a `GET` request is sent to `/api/v1/users/{id}`
* **Then** the system should return HTTP `200 OK` with the matching user as a single JSON object
```bash
curl -i -X GET http://localhost:8080/api/v1/users/1
# Expected: HTTP/1.1 200 OK
# {"id": 1, "username": "jdoe", "email": "jdoe@example.com"}
```
* **Given** a user ID that does not exist in the database
* **When** a `GET` request is sent to `/api/v1/users/{id}`
* **Then** the system should return HTTP `404 Not Found`
```bash
curl -i -X GET http://localhost:8080/api/v1/users/9999
# Expected: HTTP/1.1 404 Not Found
```


## **User Story 6: Find a User by Username**

**As an** API client

**I want to** look up a user by typing their unique username string

**So that** I can fetch a user profile without knowing their internal database ID

* **Acceptance Criteria:**
* **Given** a `username` query parameter that matches an existing record
* **When** a `GET` request is sent to `/api/v1/users?username={username}`
* **Then** the system should bypass the full collection search and return HTTP `200 OK` with that single user object
```bash
curl -i -X GET "http://localhost:8080/api/v1/users?username=jdoe"
# Expected: HTTP/1.1 200 OK
# {"id": 1, "username": "jdoe", "email": "jdoe@example.com"}
```
* **Given** a `username` query parameter that does not match any record
* **When** a `GET` request is sent to `/api/v1/users?username={username}`
* **Then** the system should return HTTP `404 Not Found`
```bash
curl -i -X GET "http://localhost:8080/api/v1/users?username=unknown"
# Expected: HTTP/1.1 404 Not Found
```


## **User Story 7: Fail Fast on Database Unavailability**

**As an** API client

**I want to** receive a clear, timely error when the underlying database cannot be reached

**So that** I can detect the outage and react (retry, alert, degrade) instead of hanging indefinitely

* **Acceptance Criteria:**
* **Given** the MariaDB database is unreachable due to network partitioning, credential expiration, or hardware failure
* **When** a connection cannot be acquired from the pool within the configured `5000ms` timeout
* **Then** the system should stop processing the request and return HTTP `500 Internal Server Error` with a standardized error payload containing a tracking UUID and an error timestamp
```bash
curl -i -X GET http://localhost:8080/api/v1/users
# (with the MariaDB instance stopped or unreachable)
# Expected: HTTP/1.1 500 Internal Server Error
# {"errorId": "b3f1c2a4-...-9e21", "timestamp": "2026-07-09T10:15:30Z"}
```
