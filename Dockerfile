FROM eclipse-temurin:25-jdk
WORKDIR /app
COPY  target/demo-hexagon-architecture-*.jar demo-hexagon-architecture.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","demo-hexagon-architecture.jar"]