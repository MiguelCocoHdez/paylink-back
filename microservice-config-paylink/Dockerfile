FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/microservice-config-paylink-*.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "app.jar"]
