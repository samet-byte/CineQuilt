# Presentation Notes


@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor

## Packages: 

### _custom
- BusinessHelper.java
- CustomFuns.kt
- RandomPasswordGenerator.kt
- SamTextFormat.kt 
  (For logging)

### auditing
- ApplicationAuditAware.java 
  (For auditing the user who sends the request)

  At the very beginning of the run of the application:
    ```java
    @SpringBootApplication
    @EnableJpaAuditing(auditorAwareRef = "auditorAware")
    public class CineQuiltAppApplication {
    ...
    ```

### config
- ApplicationConfig.java 
  (For configuring the application)
    Beans are defined here
    (For defining dependencies without creating them. IoC) 

- **JWTService and AuthenticaationFilter.java**
  (For JWT authentication)

  eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.
  eyJyb2xlcyI6WyJtYW5hZ2VtZW50OmNyZWF0ZSIsIm1hbmFnZW1lbnQ6cmVhZCIsIm1hbmFnZW1lbnQ6ZGVsZXRlIiwibWFuYWdlbWVudDp1cGRhdGUiLCJhZG1pbjpyZWFkIiwiYWRtaW46dXBkYXRlIiwiYWRtaW46Y3JlYXRlIiwiYWRtaW46ZGVsZXRlIiwiUk9MRV9BRE1JTiJdLCJhdWQiOiJjaW5lcXVpbHRhcHAiLCJzdWIiOiJhZG1pbiIsImlhdCI6MTcwMzI4NjAxNCwiZXhwIjoxNzA1ODc4MDE0fQ.
  2x3RnYZ5L1V5Q7oo5FtmX0I_I7-o-CfXVpOt-es27-8

  Example:
    ```json
    Header:
    {
        "typ": "JWT",
        "alg": "HS256"
    }
    ```
    ```json
    Payload:
    {
        "roles": [
            "management:create",
            "management:read",
            "management:delete",
            "management:update",
            "admin:read",
            "admin:update",
            "admin:create",
            "admin:delete",
            "ROLE_ADMIN"
        ],
        "aud": "cinequiltapp",
        "sub": "admin",
        "iat": 1703286014,
        "exp": 1705878014
    }
    ```

    - HMACSHA256


    ```java
        return Jwts
            .builder()
            .setHeaderParam("typ", "JWT")

            .setClaims(rolesClaim)
            .setAudience("cinequiltapp")
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
    ```

- ServerProperties.java 
  (For configuring the server)
    ```java
    @Value("${base}")
    private String baseUrl;
    ```

    ```yml
    
    application-app.yml:
    base: /api/v1
    
    ...
    application-key.yml:

    gpt:
        api-key: ***
    mail:
        host: smtp.gmail.com
        port: 587
        username: cinequilt@mail.com
        password: ***
    
    g_cloud_psql:
        url: jdbc:postgresql://global_ip:5432/cine_demo_db2
        username: filmbuff
        password: ***
    ```

## Common Properties of Entities:

#### MVC Architecture:

- Model: 
  - Entity
  - Request
  - Response

- View:

- Controller:
- Service:
- Repository:
- Exception:





Jparepository is an interface that extends the JpaRepository interface in Spring Data JPA. It provides methods for common CRUD operations on entities1. SOLID principles are a set of design guidelines for object-oriented programming that aim to improve the maintainability, readability, and flexibility of the code2.

To answer your question, Jparepository does fit some of the SOLID principles, but not all of them. Here is a brief analysis of how Jparepository relates to each principle:

Single Responsibility: Jparepository follows this principle, as it has only one responsibility: to provide CRUD operations on entities. It does not have any other logic or functionality that is unrelated to this task.
Open/Closed: Jparepository violates this principle, as it is not closed for modification. If we want to add custom methods or queries to Jparepository, we have to either extend it or use the @Query annotation. This means that we are modifying the interface, rather than extending its behavior.
Liskov Substitution: Jparepository follows this principle, as it is a subtype of JpaRepository, which is a subtype of PagingAndSortingRepository, which is a subtype of CrudRepository. This means that any class that can use a CrudRepository can also use a Jparepository, without breaking the functionality or expectations.
Interface Segregation: Jparepository violates this principle, as it is a fat interface that has many methods that may not be needed by some clients. For example, some clients may only need to save and find entities, but not delete or count them. Jparepository forces them to depend on methods that they do not use, which increases the coupling and complexity of the code.
Dependency Inversion: Jparepository follows this principle, as it depends on abstractions, not concretions. Jparepository is an interface that defines the contract for CRUD operations, but it does not specify how they are implemented. The implementation details are left to the subclasses or the framework, which can vary depending on the context and the configuration.