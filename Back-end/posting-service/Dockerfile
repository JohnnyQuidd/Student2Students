FROM openjdk:8-jdk-alpine
ADD target/post-service-0.0.1-SNAPSHOT.jar post-service-0.0.1-SNAPSHOT.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "post-service-0.0.1-SNAPSHOT.jar"]