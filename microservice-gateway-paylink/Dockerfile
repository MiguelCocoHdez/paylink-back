FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/microservice-gateway-paylink-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]