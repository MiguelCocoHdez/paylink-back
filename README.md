# Paylink (Proyecto Backend Simulado Fintech)

## Descripción del proyecto

Paylink es un proyecto backend simulado inspirado en Bizum, diseñado para **fintech y sistemas de pagos internos entre usuarios**. Está construido con **Java 21, Spring Boot 3.5.6, PostgreSQL y Kafka**, siguiendo patrones de **arquitectura hexagonal**, **DDD (Domain-Driven Design)**, **Microservicios** y **Event-driven Architecture**.  

El proyecto tiene como objetivo **aprender y demostrar buenas prácticas de backend**, incluyendo:  

- Separación de capas (Domain, Application, Infrastructure)  
- Uso de eventos mediante **Kafka** para comunicación entre microservicios (Event-driven Architecture) 
- Gestión de autenticación con **JWT y OAuth2 (Google)**  
- Contenerización de servicios con **Docker**  
- Escalabilidad y mantenibilidad de microservicios

> ⚠️ Este proyecto usa **dinero simulado**. No conecta con bancos reales.  

---

## Levantar el proyecto con Docker

### Requisitos

- Docker instalado
- Java 21 y Maven instalados localmente

### Comandos

1. Clonar el repositorio:

```bash
git clone https://github.com/MiguelCocoHdez/paylink-back
```
2. Compilar con maven

```bash
mvn clean install -DskipTests
```

4. Levantar todos los microservicios con Docker Compose:

```bash
docker compose up -d
```

### Variables de entorno

Para levantar el proyecto con docker las variables que debes definir son:

  - JWT_SECRET=tu_variable
  - EXCHANGERATE_KEY=tu API key de https://www.exchangerate-api.com

> ⚠️ Debes crear un archivo .env en la raíz del proyecto con estas variables.

---

### Desarrollo fututo

Se añadira lógica de:
  - Más microservicios (gateway, notificaciones, estadísticas)
