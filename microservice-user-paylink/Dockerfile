FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/microservice-user-paylink-*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
