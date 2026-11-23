## 🎓 Sistema de Gestión Universitaria — UAEMéx (CU Atlacomulco)

Aplicación de escritorio en **Java Swing** para la administración académica, escolar y financiera universitaria, respaldada por una base de datos relacional normalizada en **MySQL 8.0**. Implementa arquitectura desacoplada **MVC + DAO**, control de acceso basado en roles (**RBAC**) con contraseñas cifradas en **BCrypt**, transacciones **ACID**, auditoría automática mediante *triggers* y emisión de reportes y comprobantes oficiales en **PDF**.

---

## Integrantes y División de Trabajo

El desarrollo colaborativo se integró en la rama principal `main`:

| Integrante | Usuario GitHub | Rama de Desarrollo | Módulo Principal |
| :--- | :--- | :--- | :--- |
| **Gustavo Alfonso Ramírez Ramírez** | [@Gustavx2310](https://github.com/Gustavx2310) | `main` | Arquitectura MVC/DAO, Interfaz Java Swing, Conexión JDBC, Seguridad BCrypt/RBAC y Transacciones ACID |
| **Emanuel Hugo Gomez Navor**  **Eliza Paulina Martinez Luis**| [@EmanuelHugo](https://github.com/EmanuelHugo) | `main` | Diseño de la Base de Datos, Módulo de Reportes PDF y Documentación UML |

---

## Funcionalidades Principales

- **Seguridad y Control de Acceso (RBAC + BCrypt):** Inicio de sesión validando hashes criptográficos mediante `jBCrypt` (`UsuarioDAO`) y aplicación de política *fail-safe* en `FrmMenuPrincipal.aplicarReglasDeAcceso()`, restringiendo módulos dinámicamente según el rol (`SUPER_GESTOR`, `DOCENTE`, `ALUMNO`).
- **Gestión Transaccional de Pagos (ACID):** Módulo financiero (`CtrlFinanciero` / `FinancieroDAO`) con búsqueda en vivo por matrícula, candado lógico contra pagos mayores al `SaldoDeudor`, ejecución atómica (`setAutoCommit(false)`, `COMMIT`/`ROLLBACK`) de registro de pago + descuento de saldo, y emisión automática de **Recibo Oficial en PDF** con logotipo institucional (`iTextPDF`).
- **Operaciones CRUD Completas (Patrón DAO):** Administración integral de **Espacios Universitarios**, **Carreras**, **Materias**, **Docentes** (transacción `Persona` + `Docente`), **Alumnos** (transacción `Persona` + `Alumno`), **Grupos** e **Inscripciones**.
- **Auditoría y Cálculos en Base de Datos:**
  - Columna generada `CalificacionFinal` (`STORED`) calculada automáticamente en MySQL a partir de `Parcial1` y `Parcial2` en `alumno_grupo`.
  - Tabla `historial_cambios_carrera` diseñada para auditar cambios de carrera de los alumnos.
- **Generación de Reportes Institucionales (PDF):** Exportación directa de reportes tabulares de alumnos y recibos de pago en formato PDF con membrete de la UAEMéx mediante **iTextPDF 5.5**.

---

## Documentación, Base de Datos y Diagramas (12 Diagramas UML / EER)

Todos los diagramas son editables en formato **PlantUML (`.puml`)** y están documentados en [`/uml`](./uml):

- **Modelo Entidad-Relación (1):** [`/uml/01-er`](./uml/01-er) *(12 tablas normalizadas, PK/FK, columnas generadas y auditoría)*
- **Diagramas de Casos de Uso (3):** [`/uml/02-casos-de-uso`](./uml/02-casos-de-uso) *(RBAC General, Subsistema Académico, Seguridad y Finanzas)*
- **Diagramas de Clases (3):** [`/uml/03-clases`](./uml/03-clases) *(Modelo/DTO, Capa DAO/JDBC, Controladores y Vistas Swing)*
- **Diagramas de Secuencia (2):** [`/uml/04-secuencia`](./uml/04-secuencia) *(Login BCrypt + RBAC y Pago de Colegiatura ACID + PDF)*
- **Arquitectura y Estados (3):** [`/uml/05-arquitectura-y-estados`](./uml/05-arquitectura-y-estados) *(Paquetes MVC/DAO, Despliegue Cliente-Servidor y Máquina de Estados)*
- **Script de Base de Datos (estructura + datos iniciales):** [`gestion_universitaria.sql`](./gestion_universitaria.sql)

---

## Stack Tecnológico

- **Lenguaje e Interfaz Gráfica:** Java SE + Java Swing (`JFrame` / `JInternalFrame`)
- **IDE y Compilación:** Apache NetBeans — Proyecto estándar de Java con **Ant**
- **Base de Datos:** MySQL Server 8.0 (`gestion_universitaria`, motor InnoDB, `utf8mb4`)
- **Librerías Principales:**
  - `mysql-connector-j` (Conectividad JDBC)
  - `jbcrypt-0.4` (Verificación criptográfica de contraseñas)
  - `itextpdf-5.5.13.3` (Generación de reportes y recibos PDF)
- **Arquitectura:** Patrón **MVC + DAO + DTO** (`vistas`, `controladores`, `dao`, `modelo`, `dto`, `conexion`, `recursos`)

---

## Cómo Ejecutar el Proyecto

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/Gustavx2310/Proyecto-Sistema-Gesti-n-Universitaria-y-BD-.git
   cd Proyecto-Sistema-Gesti-n-Universitaria-y-BD-
   ```

2. **Importar la Base de Datos en MySQL:**
   - Abre **MySQL Workbench** y ejecuta el archivo `gestion_universitaria.sql` para crear el esquema `gestion_universitaria`, sus 12 tablas y los datos de prueba.
   - Configura tu host, usuario y contraseña de MySQL local en `sistema-gestion-universitaria/src/sistema/gestion/universitaria/conexion/ConexionDB.java`.

3. **Abrir y ejecutar en Apache NetBeans:**
   - Selecciona **File > Open Project** y abre la carpeta `sistema-gestion-universitaria`.
   - Verifica en **Properties > Libraries > Classpath** que estén vinculados los `.jar` requeridos (`mysql-connector-j`, `jbcrypt-0.4`, `itextpdf-5.5.13.3`).
   - Presiona **F6** (Run Project) para iniciar la ventana de autenticación (`FrmLogin`).

---

## Credenciales de Prueba Precargadas

Los usuarios de prueba en la tabla `usuario` cuentan con contraseñas protegidas mediante hash **BCrypt**:

| Rol | Usuario | Contraseña | Módulos Permitidos |
| :--- | :--- | :--- | :--- |
| **SUPER_GESTOR** | `gustavo.admin` | `admin123` | Acceso total (Espacios, Carreras, Materias, Docentes, Alumnos, Grupos, Inscripciones y Pagos) |
| **DOCENTE** | `carlos.docente` | `admin123` | Gestión de Grupos e Inscripciones / Calificaciones |
| **ALUMNO** | `ana.alumno` | `admin123` | Consulta de Inscripciones y Módulo de Pagos de Colegiatura |
