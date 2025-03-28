FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app
COPY ../pom.xml ../
COPY ./pom.xml ./
COPY . .

RUN mvn clean install -DskipTests
RUN mvn clean package -DskipTests spring-boot:repackage

FROM openjdk:21-jdk-slim

WORKDIR /app
COPY --from=build /app/OrderService/target/*.jar app.jar

EXPOSE 8082
CMD ["java", "-jar", "app.jar"]
