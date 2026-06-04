# bStrongGym API

API REST desarrollada con Spring Boot para la gestión de un gimnasio. Incluye gestión de socios, monitores, actividades, reservas y suscripciones.

## Tecnologías utilizadas

- Java 21
- Spring Boot 3.5
- Spring Cloud Config
- MariaDB / H2
- Docker & Docker Compose
- AWS EC2
- APIMan
- WireMock (API Mock)
- Postman / Newman
- GitHub Actions

## Repositorios del proyecto

- **API:** https://github.com/MarcosMartinezVijuesca/bStrongGym
- **Config Server:** https://github.com/MarcosMartinezVijuesca/bstrong-config-server
- **Configuración:** https://github.com/MarcosMartinezVijuesca/bstrong-config

## Requisitos previos

- Java 21
- Maven
- Docker Desktop
- MariaDB (solo para entorno de producción local)

## Cómo arrancar el proyecto en local

### 1. Arrancar el Config Server

```bash
cd bstrong-config-server
mvn spring-boot:run
```

Verificar en: `http://localhost:8888/bstrong/dev`

### 2. Arrancar la API

```bash
cd bstrong
mvn spring-boot:run
```

La API arranca en `http://localhost:8080` con perfil `dev` (H2 en memoria).

### 3. Arrancar con Docker Compose (entorno de producción)

```bash
cp .env.sample .env
# Editar .env con las credenciales reales
docker build -t bstrong-api .
docker-compose up -d
```

La API arranca en `http://localhost:8080` con perfil `prod` (MariaDB).

### 4. Entorno de desarrollo con Docker (solo base de datos)

```bash
docker-compose -f docker-compose.dev.yaml up -d
```

## Entornos

| Entorno | Base de datos | Puerto | Perfil |
|---------|--------------|--------|--------|
| Local (dev) | H2 en memoria | 8080 | dev |
| Producción | MariaDB | 8080 | prod |

## Versionado de endpoints

La API tiene endpoints versionados en `/v2/`:

| Método | v1 | v2 | Diferencia |
|--------|----|----|------------|
| GET | `/members` | `/v2/members` | v2 incluye email y birthDate |
| POST | `/members` | `/v2/members` | v2 requiere email obligatorio |
| PUT | `/activities/{id}` | `/v2/activities/{id}` | v2 incluye totalBookings |
| DELETE | `/members/{id}` | `/v2/members/{id}` | v2 devuelve mensaje de confirmación |

## API Mock con WireMock

El proyecto incluye una API Mock con WireMock que simula las respuestas de la API sin necesidad de tenerla arrancada.

### Arrancar WireMock

```bash
cd apimock-bStrong
java -jar wiremock-standalone-4.0.0-beta.21.jar --port 8080
```

Los mappings están definidos en la carpeta `mappings/` y cubren todas las operaciones CRUD de las 5 entidades incluyendo casos de error (400, 404).

Usar la colección `apimock-bstrong.postman_collection.json` para probar los mocks.

## Tests

### Tests unitarios y de controller

```bash
mvn test
```

El proyecto incluye 60 tests:
- Tests unitarios para las 5 clases de la capa Service
- Tests de controller para los casos 200, 201, 400 y 404 de cada endpoint

### Tests de integración

Los tests de integración se ejecutan automáticamente con GitHub Actions en cada push a `develop` o `main` usando Newman (runner de Postman).

## Despliegue en AWS

La API está desplegada en AWS EC2 con Docker Compose. Los contenedores `bstrong-api` y `bstrong-db` (MariaDB) corren en una red privada dentro de la instancia EC2.

La imagen de la API está publicada en Docker Hub: `marcosmartinez10/bstrong-api:latest`

## APIMan

La API está publicada en APIMan con las siguientes políticas de seguridad:
- **Rate Limiting:** máximo 10 peticiones por minuto
- **Transfer Quota:** máximo 100 MB por día

Para acceder a la API a través del gateway es necesario incluir el header `X-API-Key` en cada petición.

Usar la colección `bStrong_APIMAN_postman_collection.json` para probar la API a través del gateway.

## Colecciones Postman

| Colección | Descripción |
|-----------|-------------|
| `bStrong.postman_collection.json` | Colección principal con tests de integración |
| `bStrong_APIMAN_postman_collection.json` | Colección para pruebas a través del gateway APIMan |
| `apimock-bstrong.postman_collection.json` | Colección para pruebas contra la API Mock (WireMock) |

## Especificación OpenAPI

La especificación completa de la API está en `bstrong.yaml` en la raíz del proyecto.

## Datos de prueba

Los siguientes ficheros JSON contienen datos de prueba para poblar la base de datos:

- `member_data.json`
- `monitor_data.json`
- `activity_data.json`
- `booking_data.json`
- `subscription_data.json`
