FROM openjdk:8-jdk-alpine
ADD target/email-service-0.0.1-SNAPSHOT.jar email-service-0.0.1-SNAPSHOT.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "email-service-0.0.1-SNAPSHOT.jar"]