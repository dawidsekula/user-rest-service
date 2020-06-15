```
TO DO:
- Spring Cloud for AWS and implement AmazonSES
- better exception handling
- implement privileges as part of roles'
- updating own user's account
- tests
- HATEOAS
```


## User REST Service

Application allows user to register new account with an email verification. Registered and 
confirmed user can gain access to application by using his credentials or by generated JSON 
Web Token. User can also reset his password.

## Details

* Registration
    * Unauthenticated users can create new account
        * Request is validated
        * Invalid requests causes custom error message
    * Successful registration causes sending an email message with confirmation link
        * User cannot log in to service without verifying an email address
        * Sent token has limited-time validity
        * Re-sending confirmation link is not available for now
* Access
    * Logging in is possible in two ways, by using username and password and by passing JWT in header
    * After successful login attempt by username and password, JWT is created and added to 
    response's header
    * Sending requests with valid JWT in header is possible 
        * Token has time-limited validity
* Password reset
    * Unauthorized user can send a password reset request
        * If user's email address is in database, there is an email sent with password reset URL
        * Token in sent URL is time-limited
    * Sent URL needs to be used as request URL with proper body
        * IMPORTANT it should be refactored when front side will be implemented
    * New password is validated
* Others
    * User has implemented roles although setting roles is available through SQL only
        * User's roles are checked on every request and are not included in JWT
    * Email confirmation and password reset can be enabled in properties file (default: disabled)
    * SpringDoc with default configuration is implemented and permitted to all authorized and unauthorized users

## Configuration

* Application uses an email access for sending emails. Credentials are set as environment variables.
* Users' passwords in sample data are both `user`

## Built using

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Maven](https://maven.apache.org/)
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
* [MySQL](https://www.mysql.com/)
* [Hibernate Validation](https://hibernate.org/validator/)
* [Spring Security](https://spring.io/projects/spring-security)
* [MapStruct](https://mapstruct.org/)
* [Project Lombok](https://projectlombok.org/)
* [Passay](https://www.passay.org/)
* [SpringDoc](https://springdoc.org/)
* [auth2 JWT](https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/)


## Authors

* **Dawid Sekuła** - [GitHub](https://github.com/dawidsekula)
