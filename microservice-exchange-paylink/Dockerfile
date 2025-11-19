FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/microservice-exchange-paylink-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
