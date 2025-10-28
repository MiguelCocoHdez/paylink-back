# Base image
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copiamos el jar precompilado
COPY target/microservice-auth-paylink-*.jar app.jar

# Exponemos el puerto
EXPOSE 8081

# Ejecutamos la app
ENTRYPOINT ["java", "-jar", "app.jar"]
