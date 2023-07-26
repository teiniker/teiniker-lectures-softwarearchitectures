Example: Protocol Buffers
-------------------------------------------------------------------------------

Das "person.proto" File wird, mit Hilfe eines Code Generators, zu Java Klassen
generiert.

$ mvn test
 protobuf-maven-plugin:0.6.1:compile
 => /target/generated-sources/protobuf/java
 protobuf-maven-plugin:0.6.1:compile-custom
 => target/generated-sources/protobuf/grpc-java
 build-helper-maven-plugin:3.4.0:add-source
 => Source directory: target/generated-sources/protobuf/java added.

 maven-resources-plugin:2.6:resources
 maven-compiler-plugin:3.8.0:compile

 maven-resources-plugin:2.6:testResources
 maven-compiler-plugin:3.8.0:testCompile
 maven-surefire-plugin:2.12.4:test
