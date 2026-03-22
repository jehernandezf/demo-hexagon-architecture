# Etapa 1: Build con Maven
FROM maven:3.9.6-eclipse-temurin-25 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final ligera
FROM eclipse-temurin:25-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar demo-hexagon-architecture.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","demo-hexagon-architecture.jar"]