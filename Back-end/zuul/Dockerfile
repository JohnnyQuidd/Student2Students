FROM openjdk:8-jdk-alpine
ADD target/zuul-0.0.1-SNAPSHOT.jar zuul-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "zuul-0.0.1-SNAPSHOT.jar"]