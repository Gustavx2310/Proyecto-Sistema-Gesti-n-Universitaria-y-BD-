# 📐 Documentación UML y Modelado de Base de Datos

Este directorio contiene **12 diagramas en formato PlantUML (`.puml`)** generados directamente a partir de la arquitectura real del proyecto **Sistema de Gestión Universitaria** (Java Swing + MVC/DAO + JDBC + MySQL 8.0).

## Catálogo de Diagramas

| # | Categoría | Archivo | Descripción |
| :---: | :--- | :--- | :--- |
| 1 | **Modelo EER (BD)** | [`01-er/er_gestion_universitaria.puml`](./01-er/er_gestion_universitaria.puml) | Esquema relacional completo de las 12 tablas, PK/FK, `ENUM`, columna generada `CalificacionFinal` y auditoría. |
| 2 | **Casos de Uso** | [`02-casos-de-uso/cu_general_rbac.puml`](./02-casos-de-uso/cu_general_rbac.puml) | Matriz visual de permisos RBAC para `SUPER_GESTOR`, `DOCENTE` y `ALUMNO`. |
| 3 | **Casos de Uso** | [`02-casos-de-uso/cu_gestion_academica.puml`](./02-casos-de-uso/cu_gestion_academica.puml) | Detalle del módulo académico, reporte PDF de alumnos, inscripciones y triggers. |
| 4 | **Casos de Uso** | [`02-casos-de-uso/cu_autenticacion_y_finanzas.puml`](./02-casos-de-uso/cu_autenticacion_y_finanzas.puml) | Flujo de autenticación BCrypt y módulo de pagos ACID con generación de recibos PDF. |
| 5 | **Clases (Dominio)** | [`03-clases/clases_modelo_dominio.puml`](./03-clases/clases_modelo_dominio.puml) | Entidades del paquete `modelo` (herencia `Persona -> Alumno/Docente`) y `UsuarioDTO`. |
| 6 | **Clases (Persistencia)** | [`03-clases/clases_capa_dao_conexion.puml`](./03-clases/clases_capa_dao_conexion.puml) | Los 10 DAOs del sistema, `ConexionDB` y su dependencia con `jBCrypt`. |
| 7 | **Clases (MVC / UI)** | [`03-clases/clases_mvc_controladores_vistas.puml`](./03-clases/clases_mvc_controladores_vistas.puml) | Vistas Java Swing (`JFrame` / `JInternalFrame`) y los 9 controladores (`ActionListener`). |
| 8 | **Secuencia** | [`04-secuencia/seq_login_rbac.puml`](./04-secuencia/seq_login_rbac.puml) | Flujo paso a paso de login, validación `BCrypt.checkpw` y carga fail-safe del menú. |
| 9 | **Secuencia** | [`04-secuencia/seq_pago_colegiatura_acid.puml`](./04-secuencia/seq_pago_colegiatura_acid.puml) | Flujo transaccional ACID (`COMMIT`/`ROLLBACK`), candado de saldo y exportación PDF. |
| 10 | **Paquetes** | [`05-arquitectura-y-estados/paquetes_mvc_dao.puml`](./05-arquitectura-y-estados/paquetes_mvc_dao.puml) | Separación de responsabilidades por paquete y librerías externas (`dist/lib`). |
| 11 | **Despliegue** | [`05-arquitectura-y-estados/despliegue_sistema.puml`](./05-arquitectura-y-estados/despliegue_sistema.puml) | Topología física cliente-servidor (JVM + JDBC puerto 3306 + MySQL 8.0). |
| 12 | **Máquina de Estados** | [`05-arquitectura-y-estados/estados_entidades.puml`](./05-arquitectura-y-estados/estados_entidades.puml) | Ciclos de vida de `EstatusAcademico`, `EstatusLaboral` y `SaldoDeudor`. |

## Cómo visualizar o exportar a PNG/SVG

1. **En Visual Studio Code:** Instala la extensión **PlantUML** (`jebbs.plantuml`), abre cualquier archivo `.puml` y presiona `Alt + D` para previsualizar o exportar a PNG/SVG.
2. **En línea (sin instalar nada):** Copia el contenido de cualquier `.puml` en [PlantUML Web Server](https://www.plantuml.com/plantuml/uml/).
