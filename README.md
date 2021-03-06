# JWT Spring Security Demo

![Screenshot from running application](etc/screenshot-security-jwt-demo.png?raw=true "Screenshot JWT Spring Security Demo")

## About
This is just a simple demo for using **JSON Web Token (JWT)** with **Spring Security** and
**Spring Boot 2**. This solution is partially based on the blog entry
[REST Security with JWT using Java and Spring Security](https://www.toptal.com/java/rest-security-with-jwt-spring-security-and-java)
and the demo project [Cerberus](https://github.com/brahalla/Cerberus). Thanks to the authors!

[![Build Status](https://travis-ci.org/felix-ma/secutiry-jwt.svg?branch=master)](https://travis-ci.org/felix-ma/secutiry-jwt)

## Requirements
This demo is build with with Maven 3 and Java 1.8.

## Usage
Just start the application with the Spring Boot maven plugin (`mvn spring-boot:run`). The application is
running at [http://localhost:8080](http://localhost:8080).

There are three user accounts present to demonstrate the different levels of access to the endpoints in
the API and the different authorization exceptions:
```
Admin - admin:admin
User - user:password
Disabled - disabled:password (this user is disabled)
```

There are three endpoints that are reasonable for the demo:
```
/auth - authentication endpoint with unrestricted access
/persons - an example endpoint that is restricted to authorized users (a valid JWT token must be present in the request header)
/protected - an example endpoint that is restricted to authorized users with the role 'ROLE_ADMIN' (a valid JWT token must be present in the request header)
```

I've written a small Javascript client and put some comments in the code that hopefully makes this demo
understandable.

### Generating password hash for new users

I'm using [bcrypt](https://en.wikipedia.org/wiki/Bcrypt) to encode passwords. Your can generate your hashes with this simple tool: [Bcrypt Generator](https://www.bcrypt-generator.com)

### Using another database

Actually this demo is using an embedded H2 database that is automatically configured by Spring Boot. If you want to connect to another database you have to specify the connection in the *application.yml* in the resource directory. Here is an example for a PostgreSQL DB:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/jwt
spring.datasource.driverClassName=org.postgresql.Driver
spring.datasource.username=postgres
spring.datasource.password=postgres
```

### Using certificate generation script

Running the script generates [gen_cert.sh](script/gen_cert.sh)

`cert.pem gateway.jks gateway.p12 gateway_passphrase.pem`

Put `cert.pem` and `gateway.jks` into the project

The default configuration is as follows
```
jwt.keystore.filename=gateway.jks
jwt.key.aliase=gateway-identity-jks
jwt.keystore.password=123456
```