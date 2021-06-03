# Introduction

For Spring Boot 2.5.0 and Jersey client 2.33 the client connection timeout is double the configured if posting an entity.

```java
    client.property(ClientProperties.CONNECT_TIMEOUT, 2000);
    invocationBuilder.post(null); // correct connection timeout of 2 sec
    invocationBuilder.post(Entity.json(request)); // double connection timeout 4 sec
```

To reproduce see test `TimeoutTests`

    mvn test

Expected both requests to timeout after approximately 2 secs.

The problem was introduced with Jersey 2.31. To demonstrate downgrade Jersey in `pom.xml` to version 2.30 with

    <jersey.version>2.30</jersey.version>
