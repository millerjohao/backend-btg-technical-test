FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/spring-boot-technical-test-0.0.1-SNAPSHOT.jar /app/spring-boot-technical-test-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/spring-boot-technical-test-0.0.1-SNAPSHOT.jar"]