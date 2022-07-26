# Pact Demo Consumer

<a href="https://github.com/hansjoergkeser/pact-demo-consumer/actions?query=workflow%3A%22Java+CI+with+Maven">
<img alt="GitHub Actions status" 
src="https://github.com/actions/cache/workflows/Tests/badge.svg?branch=main&event=push">
</a>

This contains the Spring Boot `Greeting` [sample project](https://github.com/spring-guides/gs-serving-web-content),
added by some tests to demonstrate <br>
Consumer Driven Contract Testing `CDCT` with [Pact](https://pact.io/)

## Used frameworks & plugins

- [Spring Boot](https://spring.io/guides/gs/spring-boot/)
- [Spring MVC](https://spring.io/guides/gs/serving-web-content/)
- springdoc-openapi with [Swagger UI](https://swagger.io/tools/swagger-ui/)
- [Lombok](https://projectlombok.org/) to create DTOs
  <br>(for usage in IntelliJ Idea, install [Lombok Plugin](https://projectlombok.org/setup/intellij))

## Start service

after first checkout/cloning the project, execute in terminal:

```yaml
mvn clean install -U
mvn spring-boot:run
```

> Note: Service runs on localhost:9090 (see or configure `server.port` in application.properties)

### pact-demo-provider

Find the according provider service here:
<br>
https://github.com/hansjoergkeser/pact-demo-provider

### interactive documentation

use swagger ui to test and investigate
<br>
http://localhost:9090/swagger-ui/index.html

Example requests:
<br>
http://localhost:9090/user/getAllUsers
<br>
http://localhost:9090/user/1



