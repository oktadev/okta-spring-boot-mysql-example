# Spring Boot and MySQL to go Beyond Authentication

You can find the blog post that this source code backs [here](https://developer.okta.com/blog/2019/07/03/spring-boot-jpa)

This example leverages the information found in the `OidcUser` object that is automatically injected after authenticating
using [OpenId Connect](https://developer.okta.com/docs/reference/api/oidc/).

It uses [JPA](https://spring.io/projects/spring-data-jpa) to add user activity information to a [MySQL](https://www.oracle.com/mysql/) database.

## Build

In order to use this example, you'll need to create an Okta Org [here](https://developer.okta.com/signup)

You'll also need to create an `application.properties` file in `src/main/resources`:

```
okta.oauth2.issuer={authServerUrl}
okta.oauth2.clientId={clientId}
okta.oauth2.clientSecret={clientSecret}

## MySQL
spring.datasource.url=jdbc:mysql://{mysql url}:{mysql port}/{dbName}

spring.datasource.username={dbUser}
spring.datasource.password={dbPassword}

# drop in to create the table, good for testing, comment this in production. This will create the table for you on each load of application, so you may want to comment this out after the first load
spring.jpa.hibernate.ddl-auto=create
```

## Run

Once you've created an Okta org, configured it according to the [blog post](https://developer.okta.com/blog/2019/07/03/spring-boot-jpa) and set your `application.properties` file, you can run the application like so:

```
mvn spring-boot:run
```

## Additional Resources

To learn more about the Okta OIDC and Single Sign-On (SSO), check out these links:

* [Easy Single Sign-On with Spring Boot and OAuth 2.0](https://developer.okta.com/blog/2019/05/02/spring-boot-single-sign-on-oauth-2)
* [OAuth 2.0 and OpenID Connect](https://developer.okta.com/docs/concepts/auth-overview/#authentication-api-vs-oauth-2-0-vs-openid-connect)

To learn more about JPA and Hibernate, check these out:

* [Hibernate Community Documentation](https://docs.jboss.org/hibernate/annotations/3.5/reference/en/html/entity.html)
* [What is JPA? Introduction to the Java Persistence API](https://www.javaworld.com/article/3379043/what-is-jpa-introduction-to-the-java-persistence-api.html)

If you'd like to learn more about Spring Boot, Spring Security, or secure user management, check out any of these great tutorials:

- [Get Started with Spring Boot, OAuth 2.0, and Okta](https://developer.okta.com/blog/2017/03/21/spring-boot-oauth)
- [Add Single Sign-On to Your Spring Boot Web App in 15 Minutes](https://developer.okta.com/blog/2017/11/20/add-sso-spring-boot-15-min)
- [Secure Your Spring Boot Application with Multi-Factor Authentication](https://developer.okta.com/blog/2018/06/12/mfa-in-spring-boot)
- [Build a Secure API with Spring Boot and GraphQL](https://developer.okta.com/blog/2018/08/16/secure-api-spring-boot-graphql)

If you want to dive deeper, take a look at the [Okta Spring Boot Starter GitHub Project](https://github.com/okta/okta-spring-boot).

If you have any questions about this post, please add a comment below. For more awesome content, follow [@oktadev](https://twitter.com/oktadev) on Twitter, like us [on Facebook](https://www.facebook.com/oktadevelopers/), or subscribe to [our YouTube channel](https://www.youtube.com/c/oktadev).
