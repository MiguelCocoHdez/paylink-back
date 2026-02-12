# PayLink – Backend Simulado Fintech

> Plataforma de pagos internos entre usuarios inspirada en Bizum, construida con **Java 21**, **Spring Boot 3.5.6**, arquitectura de **microservicios**, **DDD**, **Arquitectura Hexagonal** y **Event-Driven Architecture** con Kafka.

---

## 📌 Índice

- [Descripción](#descripción)
- [Arquitectura](#arquitectura)
- [Microservicios](#microservicios)
- [Decisiones técnicas](#decisiones-técnicas)
- [Stack tecnológico](#stack-tecnológico)
- [Cómo levantar el proyecto](#cómo-levantar-el-proyecto)
- [Tests](#tests)

---

## Descripción

PayLink es un sistema backend de pagos simulados que permite a usuarios enviarse dinero entre sí, consultar su saldo y gestionar transacciones. **No conecta con bancos reales ni maneja dinero real.**

El objetivo del proyecto es demostrar un diseño backend production-ready, aplicando patrones y prácticas usadas en sistemas fintech reales:

- Arquitectura Hexagonal (Ports & Adapters) para máximo desacoplamiento
- DDD para modelar correctamente el dominio de pagos
- Comunicación asíncrona entre servicios mediante Apache Kafka
- Autenticación segura con JWT y OAuth2 (Google)
- Módulos Maven reutilizables para evitar duplicación de código entre servicios

---

## Arquitectura

```
                        ┌─────────────────────────────────────────────────┐
                        │                   CLIENT                        │
                        └───────────────────────┬─────────────────────────┘
                                                │ HTTP
                                                ▼
                        ┌─────────────────────────────────────────────────┐
                        │              API GATEWAY (Spring Cloud)         │
                        │         Enrutamiento · Rate limiting            │
                        └──────┬──────────┬──────────┬────────────────────┘
                               │          │          │
               ┌───────────────▼──┐  ┌────▼─────┐  ┌▼──────────────┐
               │   AUTH SERVICE   │  │  USER    │  │  TRANSACTION  │
               │  JWT + OAuth2    │  │ SERVICE  │  │   SERVICE     │
               └──────────────────┘  └──────────┘  └───────┬───────┘
                                                            │ Kafka Event
                                                            ▼
                                                   ┌────────────────┐
                                                   │    EXCHANGE    │
                                                   │    SERVICE     │
                                                   │ (divisas, FX)  │
                                                   └────────────────┘

     ┌──────────────────────────────────────────────────────────────────┐
     │                   INFRAESTRUCTURA COMPARTIDA                     │
     │   Config Server · Eureka (Service Discovery) · Apache Kafka      │
     │   Módulos Maven: kafka-events · jwt-service                      │
     └──────────────────────────────────────────────────────────────────┘
```

---

## Microservicios

| Servicio | Responsabilidad | Puerto |
|---|---|---|
| `api-gateway` | Punto de entrada único. Enruta peticiones y valida JWT | 8080 |
| `auth-service` | Registro, login, JWT y OAuth2 con Google | 8081 |
| `user-service` | Gestión de usuarios y saldos | 8082 |
| `transaction-service` | Lógica de pagos y transferencias entre usuarios | 8083 |
| `exchange-service` | Conversión de divisas en tiempo real (ExchangeRate API) | 8084 |
| `config-server` | Configuración centralizada para todos los servicios | 8888 |
| `eureka-server` | Service Discovery: registro y localización de servicios | 8761 |

### Módulos Maven compartidos

| Módulo | Descripción |
|---|---|
| `kafka-events` | DTOs y definición de eventos Kafka compartidos entre servicios |
| `jwt-service` | Lógica de generación y validación de JWT reutilizable |

> Estos módulos evitan duplicación de código y garantizan consistencia entre servicios.

---

## Decisiones técnicas

**¿Por qué Arquitectura Hexagonal?**
Separa completamente el dominio de negocio (lógica de pagos) de los detalles de infraestructura (base de datos, Kafka, HTTP). Esto permite testear la lógica de negocio de forma aislada sin levantar ningún servicio externo, y facilita cambiar implementaciones sin tocar el dominio.

**¿Por qué Kafka en vez de llamadas HTTP síncronas entre servicios?**
Las transacciones financieras no deben bloquearse esperando respuesta de otros servicios. Con Kafka, `transaction-service` emite un evento y continúa. `exchange-service` lo consume de forma asíncrona. Esto mejora la resiliencia: si `exchange-service` cae, los eventos se procesan cuando vuelve a estar disponible.

**¿Por qué módulos Maven propios (`kafka-events`, `jwt-service`)?**
En un sistema de microservicios, repetir la definición de eventos Kafka o la lógica JWT en cada servicio genera inconsistencias y deuda técnica. Extraerlos como módulos Maven garantiza una única fuente de verdad y simplifica el mantenimiento.

**¿Por qué Config Server?**
Centralizar la configuración permite cambiar propiedades de todos los servicios sin redesplegar. En producción, esto es esencial para gestionar secretos, feature flags y configuración por entorno (dev/staging/prod).

---

## Stack tecnológico

| Categoría | Tecnología |
|---|---|
| Lenguaje | Java 21 |
| Framework | Spring Boot 3.5.6 |
| Arquitectura | Microservicios, DDD, Arquitectura Hexagonal |
| Mensajería | Apache Kafka |
| Service Discovery | Spring Cloud Netflix Eureka |
| Config | Spring Cloud Config Server |
| API Gateway | Spring Cloud Gateway |
| Seguridad | Spring Security, JWT, OAuth2 (Google) |
| Base de datos | PostgreSQL |
| Contenerización | Docker, Docker Compose |
| Tests | JUnit 5, Mockito |
| CI/CD | GitHub Actions |
| API externa | ExchangeRate API |

---

## Cómo levantar el proyecto

### Requisitos previos

- Docker y Docker Compose instalados
- Java 21 y Maven instalados localmente

### Pasos

**1. Clonar el repositorio**
```bash
git clone https://github.com/MiguelCocoHdez/paylink-back
cd paylink-back
```

**2. Configurar variables de entorno**

Crea un archivo `.env` en la raíz del proyecto:
```env
JWT_SECRET=tu_secreto_jwt_aqui
EXCHANGERATE_KEY=tu_api_key_de_exchangerate-api.com
```

> Puedes obtener una API key gratuita en [exchangerate-api.com](https://www.exchangerate-api.com)

**3. Compilar los módulos compartidos**
```bash
mvn clean install -DskipTests
```

**4. Levantar todos los servicios**
```bash
docker compose up -d
```

**5. Verificar que todo está corriendo**
```bash
docker compose ps
```

Una vez levantado, el API Gateway estará disponible en `http://localhost:8080` y Eureka Dashboard en `http://localhost:8761`.

---

## Tests

El proyecto incluye tests unitarios con **JUnit 5** y **Mockito**, con especial foco en la capa de dominio y aplicación, siguiendo los principios de la Arquitectura Hexagonal: los tests no dependen de infraestructura externa.

Los tests se ejecutan automáticamente en cada push mediante el pipeline de **GitHub Actions**.

```bash
# Ejecutar tests localmente
mvn test
```

---

> ⚠️ **Aviso:** Este proyecto usa dinero simulado. No conecta con bancos reales ni procesa pagos reales.
