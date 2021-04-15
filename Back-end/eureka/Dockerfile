FROM openjdk:8-jdk-alpine
ADD target/eureka-0.0.1-SNAPSHOT.jar eureka-0.0.1-SNAPSHOT.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "eureka-0.0.1-SNAPSHOT.jar"]