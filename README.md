
# Clientes API

Una empresa necesita una solución para gestionar su lista de clientes de manera eficiente.
Actualmente, enfrentan dificultades para agregar, actualizar, eliminar y consultar información de
los clientes de forma rápida y segura.

Para resolver este desafío, se propone desarrollar un servicio RESTful que permita realizar
operaciones CRUD sobre la lista de clientes. Este servicio proporcionará una interfaz uniforme y
fácil de usar para gestionar todos los aspectos relacionados con los clientes, mejorando así la
eficiencia y la calidad de la gestión de la información del cliente.


## Requerimientos Funcionales

- Creación de un nuevo cliente.
- Obtener todos los clientes existentes.
- Obtener todos los clientes existentes que pertenezcan a un país específico.
- Obtener un cliente especifico por su identificador.
- Actualizar un cliente existente, permitiendo solo la modificación de su correo, dirección, teléfono y país.
- Eliminar un cliente por su identificador.
- Pruebas unitarias (Solo en modo de desarrollo)
## Tech Stack
- **Programming Language:** Java
- **Unit Test:** JUnit
- **API:** Quarkus
- **Database:** PostgreSQL
- **Event Bus:** Kafka
- **Containers:** Docker
## Arquitectura de Aplicacion

![Arquitectura de Aplicacion](https://github.com/KelfiBM/backend-developer-lite/blob/master/supporting-documents/diagrama-de-arquitectura-dark.svg?raw=true)
## Ejecutar localmente

Clonar el proyecto

```bash
  git clone https://github.com/KelfiBM/backend-developer-lite.git
```

Ir al directorio del proyecto

```bash
  cd my-project
```

Iniciar la app

```bash
  ./mvnw quarkus:dev
```

Navegar al Quarkus DevUI en <http://localhost:8080/q/dev/>
## API Overview

- **Default Url**: [localhost:8080/api/clientes](<http://localhost:8080/api/clientes>)
- **Base Path**: `/api/clientes`
- **Authentication**: Not Required
- OpenAPI available using Swagger UI at: [Swagger UI](<http://localhost:8080/q/dev-ui/io.quarkus.quarkus-smallrye-openapi/swagger-ui>)

## Endpoints

### Listado de todos los Clientes
```http
GET /api/clientes
```
**Response**: 200 OK  
Retorna una lista de todos los clientes.

### Obtener Cliente por ID
```http
GET /api/clientes/{id}
```
**Response**: 200 OK  
Retorna la informacion de cliente para el ID especificado

### Agregar Cliente
```http
POST /api/clientes
Content-Type: application/json
```
**Body Example**:
```json
{
  "primerNombre": "Juan",
  "segundoNombre": "Carlos",
  "primerApellido": "Fermin",
  "segundoApellido": "Lopez",
  "correo": "juan@example.com",
  "direccion": "Av. Francisco Prat Ramirez",
  "telefono": "8091234567",
  "pais": "DOM"
}
```
**Response**: 200 OK or 400 Bad Request

#### Notas de validacion
- Campos obligatorios: `primerNombre`, `primerApellido`, `correo`, `direccion`, `telefono`, `pais`
- `pais`: Debe ser un codigo de 3 letras como indica ISO 3166.
- `telefono`: Debe ser numerico y de maximo 15 digitos

### Actualizar Cliente
```http
PUT /api/clientes/{id}
Content-Type: application/json
```
**Body Example**:
```json
{
  "correo": "nuevo@example.com",
  "telefono": "8099876543",
  "direccion": "Av. Ortega y Gasset",
  "pais": "USA"
}
```
**Response**: 200 OK or 400 Bad Request

#### Notas de validacion
- Todos los campos son opcionales
- `pais`: Debe ser un codigo de 3 letras como indica ISO 3166.
- `telefono`: Debe ser numerico y de maximo 15 digitos

### Eliminar Cliente
```http
DELETE /api/clientes/{id}
```
**Response**: 204 No Content

---

### Contar total de Clientes
```http
GET /api/clientes/count
```
**Response**: 200 OK  
Retorna el numero total de clientes guardados.

---

### Buscar Clientes
```http
GET /api/clientes/search?pais=DOM
```
**Response**: 200 OK  
Retorna un listado de clientes que cumplan con los filtros.

#### Notas de validacion
- Campos disponibles: `pais`
- `pais`: Debe ser un codigo de 3 letras como indica ISO 3166.

---

## Modelo de Datos

### Cliente
| Field            | Type    | Description          |
|------------------|---------|----------------------|
| id               | int64   | Cliente ID           |
| primerNombre     | string  | Primer Nombre        |
| segundoNombre    | string  | Segundo Nombre       |
| primerApellido   | string  | Primer Apellido      |
| segundoApellido  | string  | Segundo Apellido     |
| correo           | string  | Email o Correo       |
| direccion        | string  | Dirección            |
| telefono         | string  | Telefono             |
| pais             | object  | Información de Pais  |

### Pais
| Field       | Type    | Description                        |
|-------------|---------|------------------------------------|
| id          | int64   | Pais ID                            |
| codeCCA3    | string  | Código del Pais (ISO 3166)         |
| name        | string  | Nombre del Pais                    |
| demonymM    | string  | Gentilicio Masculino (Ingles)      |
| demonymF    | string  | Gentilicio Femenino (Ingles)       |
| deprecated  | boolean | Estado del Codigo del Pais         |

## Ejecutar Tests

Con la aplicacion corriendo, ir a la seccion de seccion de [Continuous Testing](<http://localhost:8080/q/dev-ui/continuous-testing>) en el [Quarkus DevUI](<http://localhost:8080/q/dev/>) o navegar a:

```
  http://localhost:8080/q/dev-ui/continuous-testing
```
