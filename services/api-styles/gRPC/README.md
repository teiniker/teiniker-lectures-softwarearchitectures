# gRPC 

## Introduction

**gRPC** (Google Remote Procedure Call) is a high-performance, open-source
RPC framework originally developed by Google. It allows a **client application**
to directly call methods on a **server application** on a different machine as
if it were a local object, making it easier to build distributed applications
and services.

gRPC uses **HTTP/2** as its transport protocol, which provides features such
as multiplexing, flow control, header compression, and bidirectional
streaming. By default, gRPC uses **Protocol Buffers** as its interface
definition language (IDL) and as its underlying message interchange format.

**Key benefits of gRPC:**
- **Performance**: Binary serialization with Protocol Buffers is significantly
  faster and smaller than JSON or XML.
- **Strongly typed contracts**: The `.proto` file defines both the API and
  the message format, enabling automatic code generation.
- **Streaming support**: gRPC supports unary, server-streaming,
  client-streaming, and bidirectional-streaming RPCs.
- **Cross-language**: Code can be generated for many languages (Java, Go,
  Python, C++, etc.) from a single `.proto` file.
- **Interoperability**: Any gRPC client can communicate with any gRPC server,
  regardless of the implementation language.

**Typical use cases:**
- Microservice-to-microservice communication inside a backend system.
- Mobile or IoT clients communicating with a backend service.
- High-throughput, low-latency APIs where JSON overhead is a concern.
- Real-time streaming of data between services.


## Protocol Buffers

**Protocol Buffers** (protobuf) is Google's language-neutral, platform-neutral
mechanism for serializing structured data. It is more efficient than XML or
JSON because it uses a compact binary format.

A message is defined in a `.proto` file and compiled to a Java class that
provides a **builder API** for constructing instances, and methods for binary
serialization and deserialization.

```proto
syntax = "proto3";
option java_multiple_files = true;
package org.se.lab;

message Person
{
    int32  id       = 1;
    string name     = 2;
    string password = 3;
}

message AddressBook
{
    repeated Person people = 1;
}
```

Each field carries a **unique field number** (the `= 1`, `= 2`, etc.).
These numbers identify the fields in the binary encoding, not the field names.
This allows the schema to evolve without breaking existing serialized data.

The keyword `repeated` marks a field that can appear zero or more times,
equivalent to a list.

Once the proto file is compiled to Java, the generated classes expose a
builder API:

```java
Person person = Person.newBuilder()
        .setId(7)
        .setName("homer")
        .setPassword("$2y$12$9gRSvDCPp9lC...")
        .build();
```

Accessing fields uses generated getters:

```java
Assert.assertEquals(7,       person.getId());
Assert.assertEquals("homer", person.getName());
```

Binary serialization and deserialization are straightforward:

```java
// Serialize to bytes
byte[] bytes = person.toByteArray();

// Deserialize from bytes
Person clone = Person.parseFrom(bytes);
```

Composite messages such as `AddressBook` are built the same way:

```java
AddressBook book = AddressBook.newBuilder()
        .addPeople(marge)
        .addPeople(homer)
        .build();

Assert.assertEquals(2,       book.getPeopleCount());
Assert.assertEquals("homer", book.getPeople(1).getName());
```

The text representation produced by `toString()` uses the protobuf text
format, which is human-readable but not the binary wire format:

```
id: 7
name: "homer"
password: "$2y$12$9gRSvDCPp9lC..."
```


## Proto File

A `.proto` file is the **single source of truth** for a gRPC API. It defines:
- The **syntax version** (`proto3` is current).
- The **package** and Java code-generation options.
- The **message types** used as request and response payloads.
- The **service** with its RPC method signatures.

```proto
syntax = "proto3";
option java_multiple_files = true;
package org.se.lab;

message HelloRequest
{
    string firstName = 1;
    string lastName  = 2;
}

message HelloResponse
{
    string greeting = 1;
}

service HelloService
{
    rpc hello(HelloRequest) returns (HelloResponse);
}
```

**`syntax = "proto3"`** -- selects the third version of the proto language.

**`option java_multiple_files = true`** -- instructs the compiler to generate
each top-level message and service in its own `.java` file instead of nesting
everything in one outer class.

**`package org.se.lab`** -- sets the Java package for all generated classes.

**Messages** map to immutable Java classes. Each field has a scalar or
composite type and a unique field number used in the binary encoding.

**Services** declare one or more RPC methods. Each method takes exactly one
request message type and returns exactly one response message type.
Streaming variants prefix the type with `stream`.

### Code Generation with Maven

The `protobuf-maven-plugin` drives code generation during the Maven build:

```xml
<plugin>
    <groupId>org.xolstice.maven.plugins</groupId>
    <artifactId>protobuf-maven-plugin</artifactId>
    <version>0.6.1</version>
    <configuration>
        <protocArtifact>
            com.google.protobuf:protoc:3.3.0:exe:${os.detected.classifier}
        </protocArtifact>
        <pluginId>grpc-java</pluginId>
        <pluginArtifact>
            io.grpc:protoc-gen-grpc-java:1.4.0:exe:${os.detected.classifier}
        </pluginArtifact>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>compile</goal>
                <goal>compile-custom</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

Running `mvn test` triggers the following steps:

1. `protobuf-maven-plugin:compile` generates message classes into
   `target/generated-sources/protobuf/java/`.
2. `protobuf-maven-plugin:compile-custom` generates the service stub into
   `target/generated-sources/protobuf/grpc-java/`.
3. `build-helper-maven-plugin:add-source` registers the generated directories
   as source roots so the normal compiler picks them up.

The generated files for the `HelloService` example are:
- **`HelloRequest.java`** -- immutable message class with a builder.
- **`HelloResponse.java`** -- immutable message class with a builder.
- **`HelloServiceGrpc.java`** -- service base class and client stub factory.


## Services

### Implementing the Server

The generated `HelloServiceGrpc.HelloServiceImplBase` is an abstract class
that provides empty implementations of all service methods. Override the
methods you want to expose:

```java
public class HelloServiceImpl
    extends HelloServiceGrpc.HelloServiceImplBase
{
    @Override
    public void hello(
            HelloRequest request,
            StreamObserver<HelloResponse> responseObserver)
    {
        String greeting = new StringBuilder()
                .append("Hello, ")
                .append(request.getFirstName())
                .append(" ")
                .append(request.getLastName())
                .toString();

        HelloResponse response = HelloResponse.newBuilder()
                .setGreeting(greeting)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
```

The method does not return `HelloResponse` directly. Instead it receives a
`StreamObserver<HelloResponse>` callback object:
- `onNext(response)` sends the response message to the client.
- `onCompleted()` signals the end of the RPC. Omitting this call leaves the
  connection open and the client waiting indefinitely.

Start the server with `ServerBuilder`:

```java
public class GRPCServer
{
    public static void main(String[] args)
            throws IOException, InterruptedException
    {
        Server server = ServerBuilder
                .forPort(8080)
                .addService(new HelloServiceImpl())
                .build();

        server.start();
        System.out.println("Server started!");
        server.awaitTermination();
    }
}
```

`awaitTermination()` blocks the main thread so the server keeps running
until it is shut down externally.

### Implementing the Client

The client creates a **managed channel** and wraps it in a **stub**:

```java
@Test
public void testHelloService()
{
    ManagedChannel channel = ManagedChannelBuilder
            .forAddress("localhost", 8080)
            .usePlaintext()
            .build();

    HelloServiceGrpc.HelloServiceBlockingStub stub
            = HelloServiceGrpc.newBlockingStub(channel);

    HelloResponse response = stub.hello(HelloRequest.newBuilder()
            .setFirstName("Homer")
            .setLastName("Simpson")
            .build());

    System.out.println("Response received from server:\n" + response);
    channel.shutdown();
}
```

**`ManagedChannel`** abstracts the underlying network connection including
connection pooling and load balancing. `usePlaintext()` disables TLS, which
is acceptable for local testing but not for production.

**`HelloServiceBlockingStub`** makes synchronous (blocking) calls: the thread
waits until the server responds or an error is raised. An asynchronous
`HelloServiceStub` also exists for non-blocking calls via callbacks.

After use, `channel.shutdown()` releases the network resources.


## References

* [Introduction to Google Protocol Buffer](https://www.baeldung.com/google-protocol-buffer)
* [Introduction to gRPC](https://www.baeldung.com/grpc-introduction)

*Egon Teiniker, 2016-2026, GPL v3.0*
