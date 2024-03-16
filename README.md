# A Book Library backend service with Spring Boot and H2 Database

This is a sample Book Library backend service with Spring Boot and H2 Database.


## To run this Spring Boot application
```
mvn spring-boot:run
```

## The generated open api documentation UI is available on the following URL:
http://localhost:8080/swagger-ui/index.html

## The generated open api documentation yml is available on the following URL:
http://localhost:8080/v3/api-docs.yaml

## The H2 database consolle is available on the following URL:
http://localhost:8080/h2-ui/
JDBC URL: jdbc:h2:file:./book-library-db
User name: sa
No password required for this sample. 

## Authentication
This version is an authenticated version.

APIs are protected by oauth2 authentication. This server works as the resource server. 

This is just a minimum version. Roles and access are not well handled yet. Error handling is not fine tuned yet.

Client secrets are just hardcoded in the application.properties. In production secrets should be stored in a more secured environment. 

The source code of the auth server is available at https://github.com/dorjear/oauth2-auth-server.

Auth server need to be started before started this server.
