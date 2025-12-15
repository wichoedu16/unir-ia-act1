# romero_act1 (Local)

Aplicación **Spring Boot 3 + Java 21 + MVC (Thymeleaf) + JPA + H2 (archivo) + Spring Security**.  
Incluye UI con Bootstrap (CDN) y CRUD para **Tasks** (y módulos Runbooks/Incidents si están incluidos en tu base).

---

## Requisitos

- **Java 21+**
- No necesitas instalar Maven: el proyecto usa **Maven Wrapper** (`./mvnw`)

Verifica Java:
```bash
java -version

```

---

## Ejecución

1. Navega al directorio del proyecto:
   ```bash
   cd romero_act1
   ```
2. Compila (sin tests) para validar:
   ```bash
   ./mvnw -q -DskipTests package
   ```

3. Levanta la app:
   ```bash
   ./mvnw spring-boot:run
   ```

4. La app quedará disponible en:
   
   * http://localhost:8080
   

---

## Funcionalidades

- **CRUD para Tasks**: Crear, leer, actualizar y eliminar tareas.
- **Autenticación**: Login con usuario `admin` y contraseña `admin123`.
- **Base de datos H2**: Persistencia en archivo (`./data/romero_act1.mv.db`).

---

## Configuración

- **Base de datos**: Configurada en `src/main/resources/application.properties`.
- **Seguridad**: Configurada en `SecurityConfig.java`.

---
