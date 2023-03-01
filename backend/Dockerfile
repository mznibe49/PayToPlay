# Build stage
FROM maven:3.8.4-jdk-11 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:11-jre-slim
WORKDIR /app
ARG JAR_FILE=*.jar
COPY --from=build /app/target/${JAR_FILE} application.jar
CMD ["java", "-jar", "application.jar"]