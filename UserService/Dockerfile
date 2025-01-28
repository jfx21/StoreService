FROM ubuntu:latest
LABEL authors="jfx"

FROM openjdk:21-jdk-alpine
WORKDIR /app
COPY . .
RUN mvn clean package
EXPOSE  8081
CMD ["java", "-jar", "target/UserService-1.0-SNAPSHOT.jar"]