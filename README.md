# AsistControl - Sistema de Control de Asistencias

API REST completa para sistema de control de asistencias de empleados con GPS y biometría.

## 🚀 Características

- **Gestión de Empleados**: CRUD completo con validaciones
- **Control de Asistencias**: Registro entrada/salida con GPS y validación biométrica
- **Seguridad JWT**: Autenticación básica con tokens
- **Validación GPS**: Geocercas por sede con cálculo de distancia
- **Biometría**: Simulación de verificación de huella dactilar y reconocimiento facial
- **API Documentation**: Swagger/OpenAPI 3.0
- **Base de Datos**: PostgreSQL con JPA/Hibernate

## 🛠️ Tecnologías

- **Java 17**
- **Spring Boot 3.1.5**
- **Spring Security** (JWT)
- **Spring Data JPA**
- **PostgreSQL**
- **Swagger/OpenAPI 3.0**
- **Maven**

## 📋 Prerrequisitos

1. Java 17 o superior
2. PostgreSQL 13+ instalado y ejecutándose
3. Maven 3.8+
4. IntelliJ IDEA (recomendado)

## ⚙️ Configuración

### 1. Base de Datos

Crear base de datos PostgreSQL:

```sql
CREATE DATABASE asist_control_db;
CREATE USER asist_user WITH ENCRYPTED PASSWORD 'asist_password';
GRANT ALL PRIVILEGES ON DATABASE asist_control_db TO asist_user;
```

### 2. Configuración de Aplicación

El archivo `application.yml` está preconfigurado con:
- Puerto: 8080
- Base de datos: PostgreSQL local
- JWT secret y expiración
- Configuración Swagger

### 3. Ejecutar la Aplicación

```bash
# Clonar y navegar al proyecto
cd AsistControl

# Compilar
mvn clean compile

# Ejecutar
mvn spring-boot:run
```

## 🧪 Datos de Prueba

Al iniciar la aplicación, se cargan automáticamente:

### Usuarios del Sistema
- **Admin**: `admin` / `admin123`
- **Usuario**: `user` / `user123`

### Datos de Empresa
- **Empresa**: Empresa Demo S.A.C. (RUC: 20123456789)
- **Sedes**: 
  - Sede Principal - Lima (Lat: -12.0464, Lng: -77.0428)
  - Sucursal Miraflores (Lat: -12.1189, Lng: -77.0315)

### Empleados de Prueba
- **EMP001**: Juan Carlos Pérez García (DNI: 12345678)
- **EMP002**: María Elena González López (DNI: 87654321)  
- **EMP003**: Carlos Alberto Ramírez Torres (DNI: 11223344)

## 📚 API Endpoints

### Autenticación
- `POST /api/auth/login` - Iniciar sesión
- `POST /api/auth/validate` - Validar token

### Empleados
- `GET /api/empleados` - Listar empleados
- `GET /api/empleados/{id}` - Obtener por ID
- `GET /api/empleados/codigo/{codigo}` - Obtener por código
- `POST /api/empleados` - Crear empleado
- `PUT /api/empleados/{id}` - Actualizar empleado
- `DELETE /api/empleados/{id}` - Eliminar empleado
- `GET /api/empleados/buscar?termino=` - Buscar empleados

### Asistencias
- `POST /api/asistencias/registrar` - Registrar asistencia (sin auth)
- `GET /api/asistencias` - Listar asistencias
- `GET /api/asistencias/empleado/{id}` - Por empleado
- `GET /api/asistencias/hoy` - Asistencias de hoy
- `PUT /api/asistencias/{id}/validar` - Validar asistencia

## 📖 Documentación API

Una vez iniciada la aplicación, acceder a:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## 🧪 Pruebas con Postman

1. Importar la colección: `postman/AsistControl_API_Collection.json`
2. Configurar variables:
   - `baseUrl`: http://localhost:8080/api
3. Ejecutar pruebas en orden:
   - Authentication → Employee Management → Attendance Management

## 🔐 Seguridad

### JWT Authentication
- Token válido por 24 horas
- Requerido para endpoints de empleados y consulta de asistencias
- El registro de asistencias NO requiere autenticación (para apps móviles)

### Roles
- **ADMIN**: Acceso completo
- **USER**: Lectura y registro de asistencias

## 🌍 Validación GPS

### Geocerca
- Radio configurable por sede (default: 100m)
- Cálculo automático de distancia usando fórmula Haversine
- Validación automática si está dentro del radio

### Coordenadas de Prueba
```json
{
  "latitud": -12.0464,
  "longitud": -77.0428
}
```

## 🔒 Validación Biométrica

### Huella Dactilar
- Formato: Base64 encoded
- Simulación de comparación con umbral 85%
- Ejemplo: `"U2FsdGVkX1+E4dYEczY8EnCwQPhMTqjCzQGFJJGNpbI="`

### Reconocimiento Facial
- Formato: Base64 encoded
- Simulación de comparación con umbral 80%
- Ejemplo: `"iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJ..."`

## 📊 Esquema de Base de Datos

```
Empresa (1) → (N) Sede (1) → (N) Empleado (1) → (N) Asistencia
```

### Entidades Principales:
- **Empresa**: Datos de la empresa
- **Sede**: Sucursales con geocercas
- **Empleado**: Personal con datos biométricos
- **Asistencia**: Registros de entrada/salida con GPS

## ⚡ Funcionalidades Clave

### Registro de Asistencia
1. Validación de empleado activo
2. Verificación de tipo de registro (evita duplicados)
3. Cálculo de distancia GPS
4. Validación biométrica opcional
5. Auto-validación si cumple criterios
6. Captura de metadata (IP, dispositivo)

### Validación Automática
Se valida automáticamente si:
- Está dentro de la geocerca
- Huella dactilar verificada O reconocimiento facial exitoso

## 🚨 Manejo de Errores

- Validaciones de entrada con mensajes descriptivos
- Manejo de duplicados (código empleado, documento)
- Validación de reglas de negocio (horarios, ubicación)
- Respuestas HTTP apropiadas con mensajes de error

## 🔧 Desarrollo

### Estructura del Proyecto
```
src/main/java/com/asistcontrol/api/
├── config/          # Configuraciones (Security, Swagger, Data)
├── controller/      # REST Controllers
├── dto/            # Data Transfer Objects
├── entity/         # Entidades JPA
├── repository/     # Repositorios JPA
├── security/       # JWT y autenticación
└── service/        # Lógica de negocio
```

### Comandos Útiles
```bash
# Compilar
mvn clean compile

# Ejecutar tests
mvn test

# Generar JAR
mvn clean package

# Ejecutar JAR
java -jar target/asist-control-api-0.0.1-SNAPSHOT.jar
```

## 📝 Próximas Mejoras

- [ ] Implementar librería real de biometría
- [ ] Agregar reportes de asistencia
- [ ] Dashboard web administrativo
- [ ] Notificaciones push
- [ ] Integración con sistemas de nómina
- [ ] App móvil nativa

## 🤝 Contribución

1. Fork el proyecto
2. Crear rama feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crear Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE.md](LICENSE.md) para más detalles.