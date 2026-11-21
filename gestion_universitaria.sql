CREATE DATABASE  IF NOT EXISTS `gestion_universitaria` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `gestion_universitaria`;
-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: gestion_universitaria
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `alumno`
--

DROP TABLE IF EXISTS `alumno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alumno` (
  `IdAlumno` int NOT NULL,
  `Matricula` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `IdCarreraActual` int NOT NULL,
  `SemestreActual` int NOT NULL DEFAULT '1',
  `EstatusAcademico` enum('Inscrito','Baja Temporal','Baja Definitiva','Egresado') COLLATE utf8mb4_unicode_ci DEFAULT 'Inscrito',
  `SaldoPendiente` decimal(10,2) DEFAULT '0.00',
  `SaldoDeudor` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`IdAlumno`),
  UNIQUE KEY `Matricula` (`Matricula`),
  KEY `IdCarreraActual` (`IdCarreraActual`),
  CONSTRAINT `alumno_ibfk_1` FOREIGN KEY (`IdAlumno`) REFERENCES `persona` (`IdPersona`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `alumno_ibfk_2` FOREIGN KEY (`IdCarreraActual`) REFERENCES `carrera` (`IdCarrera`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alumno`
--

LOCK TABLES `alumno` WRITE;
/*!40000 ALTER TABLE `alumno` DISABLE KEYS */;
INSERT INTO `alumno` VALUES (1,'202600101',1,1,'Inscrito',0.00,-100.00),(2,'202600102',2,1,'Inscrito',0.00,-100.00),(5,'202600103',2,1,'Inscrito',0.00,1340.00);
/*!40000 ALTER TABLE `alumno` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alumno_grupo`
--

DROP TABLE IF EXISTS `alumno_grupo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alumno_grupo` (
  `IdAlumno` int NOT NULL,
  `IdGrupo` int NOT NULL,
  `Parcial1` decimal(4,2) DEFAULT '0.00',
  `Parcial2` decimal(4,2) DEFAULT '0.00',
  `FechaInscripcion` datetime DEFAULT CURRENT_TIMESTAMP,
  `CalificacionFinal` decimal(5,2) GENERATED ALWAYS AS (((`Parcial1` + `Parcial2`) / 2)) STORED,
  PRIMARY KEY (`IdAlumno`,`IdGrupo`),
  KEY `IdGrupo` (`IdGrupo`),
  CONSTRAINT `alumno_grupo_ibfk_1` FOREIGN KEY (`IdAlumno`) REFERENCES `alumno` (`IdAlumno`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `alumno_grupo_ibfk_2` FOREIGN KEY (`IdGrupo`) REFERENCES `grupo` (`IdGrupo`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alumno_grupo`
--

LOCK TABLES `alumno_grupo` WRITE;
/*!40000 ALTER TABLE `alumno_grupo` DISABLE KEYS */;
/*!40000 ALTER TABLE `alumno_grupo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carrera`
--

DROP TABLE IF EXISTS `carrera`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carrera` (
  `IdCarrera` int NOT NULL AUTO_INCREMENT,
  `IdEspacio` int NOT NULL,
  `NombreCarrera` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Estatus` enum('Activo','En Liquidacion','Inactivo') COLLATE utf8mb4_unicode_ci DEFAULT 'Activo',
  PRIMARY KEY (`IdCarrera`),
  KEY `IdEspacio` (`IdEspacio`),
  CONSTRAINT `carrera_ibfk_1` FOREIGN KEY (`IdEspacio`) REFERENCES `espaciouniversitario` (`IdEspacio`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrera`
--

LOCK TABLES `carrera` WRITE;
/*!40000 ALTER TABLE `carrera` DISABLE KEYS */;
INSERT INTO `carrera` VALUES (1,1,'Ingeniería en Computación','Inactivo'),(2,1,'Licenciatura en Administración','Activo'),(3,1,'Ingeniería en Software','Activo'),(4,1,'Ingeniería en Computación','Activo');
/*!40000 ALTER TABLE `carrera` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `docente`
--

DROP TABLE IF EXISTS `docente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `docente` (
  `IdDocente` int NOT NULL,
  `NumeroEmpleado` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `EstatusLaboral` enum('Activo','Inactivo','Jubilado','Licencia') COLLATE utf8mb4_unicode_ci DEFAULT 'Activo',
  PRIMARY KEY (`IdDocente`),
  UNIQUE KEY `NumeroEmpleado` (`NumeroEmpleado`),
  CONSTRAINT `docente_ibfk_1` FOREIGN KEY (`IdDocente`) REFERENCES `persona` (`IdPersona`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `docente`
--

LOCK TABLES `docente` WRITE;
/*!40000 ALTER TABLE `docente` DISABLE KEYS */;
INSERT INTO `docente` VALUES (3,'EMP-9001','Activo'),(4,'EMP-9002','Activo');
/*!40000 ALTER TABLE `docente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `espaciouniversitario`
--

DROP TABLE IF EXISTS `espaciouniversitario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `espaciouniversitario` (
  `IdEspacio` int NOT NULL AUTO_INCREMENT,
  `NombreOficial` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Estatus` enum('Activo','Inactivo') COLLATE utf8mb4_unicode_ci DEFAULT 'Activo',
  PRIMARY KEY (`IdEspacio`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `espaciouniversitario`
--

LOCK TABLES `espaciouniversitario` WRITE;
/*!40000 ALTER TABLE `espaciouniversitario` DISABLE KEYS */;
INSERT INTO `espaciouniversitario` VALUES (1,'Centro Universitario UAEM Atlacomulco','Activo'),(2,'Facultad de Ingeniería','Activo'),(3,'Facultad de Ciencias','Activo');
/*!40000 ALTER TABLE `espaciouniversitario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupo`
--

DROP TABLE IF EXISTS `grupo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grupo` (
  `IdGrupo` int NOT NULL AUTO_INCREMENT,
  `ClaveGrupo` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `IdMateria` int NOT NULL,
  `IdDocente` int NOT NULL,
  `PeriodoEscolar` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`IdGrupo`),
  KEY `IdMateria` (`IdMateria`),
  KEY `IdDocente` (`IdDocente`),
  CONSTRAINT `grupo_ibfk_1` FOREIGN KEY (`IdMateria`) REFERENCES `materia` (`IdMateria`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `grupo_ibfk_2` FOREIGN KEY (`IdDocente`) REFERENCES `docente` (`IdDocente`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupo`
--

LOCK TABLES `grupo` WRITE;
/*!40000 ALTER TABLE `grupo` DISABLE KEYS */;
INSERT INTO `grupo` VALUES (1,'ICO-30',1,3,'2026B'),(2,'ICO-31',2,4,'2026B'),(3,'ICO-10',3,3,'2026B');
/*!40000 ALTER TABLE `grupo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historial_cambios_carrera`
--

DROP TABLE IF EXISTS `historial_cambios_carrera`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historial_cambios_carrera` (
  `IdCambio` int NOT NULL AUTO_INCREMENT,
  `IdAlumno` int NOT NULL,
  `IdCarreraAnterior` int DEFAULT NULL,
  `IdCarreraNueva` int NOT NULL,
  `FechaCambio` datetime DEFAULT CURRENT_TIMESTAMP,
  `MotivoCambio` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`IdCambio`),
  KEY `IdAlumno` (`IdAlumno`),
  KEY `IdCarreraAnterior` (`IdCarreraAnterior`),
  KEY `IdCarreraNueva` (`IdCarreraNueva`),
  CONSTRAINT `historial_cambios_carrera_ibfk_1` FOREIGN KEY (`IdAlumno`) REFERENCES `alumno` (`IdAlumno`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `historial_cambios_carrera_ibfk_2` FOREIGN KEY (`IdCarreraAnterior`) REFERENCES `carrera` (`IdCarrera`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `historial_cambios_carrera_ibfk_3` FOREIGN KEY (`IdCarreraNueva`) REFERENCES `carrera` (`IdCarrera`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historial_cambios_carrera`
--

LOCK TABLES `historial_cambios_carrera` WRITE;
/*!40000 ALTER TABLE `historial_cambios_carrera` DISABLE KEYS */;
INSERT INTO `historial_cambios_carrera` VALUES (1,1,NULL,1,'2026-10-14 23:32:37','Ingreso a la universidad'),(2,2,NULL,1,'2026-10-14 23:32:37','Ingreso a la universidad'),(3,5,NULL,2,'2026-10-14 23:32:37','Ingreso a la universidad'),(4,2,1,2,'2026-11-09 10:29:23','Cambio automatizado detectado por Trigger');
/*!40000 ALTER TABLE `historial_cambios_carrera` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `materia`
--

DROP TABLE IF EXISTS `materia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `materia` (
  `IdMateria` int NOT NULL AUTO_INCREMENT,
  `IdCarrera` int NOT NULL,
  `NombreMateria` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Creditos` int NOT NULL,
  `Estatus` enum('Activa','Inactiva') COLLATE utf8mb4_unicode_ci DEFAULT 'Activa',
  PRIMARY KEY (`IdMateria`),
  KEY `IdCarrera` (`IdCarrera`),
  CONSTRAINT `materia_ibfk_1` FOREIGN KEY (`IdCarrera`) REFERENCES `carrera` (`IdCarrera`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materia`
--

LOCK TABLES `materia` WRITE;
/*!40000 ALTER TABLE `materia` DISABLE KEYS */;
INSERT INTO `materia` VALUES (1,1,'Bases de Datos',8,'Activa'),(2,1,'Programación Orientada a Objetos',8,'Activa'),(3,1,'Estructura de Datos',8,'Activa'),(4,1,'Ingeniería de Software',10,'Activa');
/*!40000 ALTER TABLE `materia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pagos_colegiatura`
--

DROP TABLE IF EXISTS `pagos_colegiatura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pagos_colegiatura` (
  `IdPago` int NOT NULL AUTO_INCREMENT,
  `IdAlumno` int NOT NULL,
  `Monto` decimal(10,2) NOT NULL,
  `FechaPago` datetime DEFAULT CURRENT_TIMESTAMP,
  `Recibo` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`IdPago`),
  UNIQUE KEY `Recibo` (`Recibo`),
  KEY `IdAlumno` (`IdAlumno`),
  CONSTRAINT `pagos_colegiatura_ibfk_1` FOREIGN KEY (`IdAlumno`) REFERENCES `alumno` (`IdAlumno`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagos_colegiatura`
--

LOCK TABLES `pagos_colegiatura` WRITE;
/*!40000 ALTER TABLE `pagos_colegiatura` DISABLE KEYS */;
INSERT INTO `pagos_colegiatura` VALUES (1,1,150.00,'2026-11-09 20:45:05','001'),(2,1,1600.00,'2026-11-09 21:11:44','002'),(3,2,1600.00,'2026-11-09 21:13:24','003'),(4,5,160.00,'2026-11-10 07:23:32','005');
/*!40000 ALTER TABLE `pagos_colegiatura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persona`
--

DROP TABLE IF EXISTS `persona`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persona` (
  `IdPersona` int NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ApellidoPaterno` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ApellidoMaterno` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `FechaRegistro` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`IdPersona`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persona`
--

LOCK TABLES `persona` WRITE;
/*!40000 ALTER TABLE `persona` DISABLE KEYS */;
INSERT INTO `persona` VALUES (1,'Gustavo Alfonso','Ramírez','Ramírez','2026-10-14 23:32:37'),(2,'Ana Laura','Gómez','López','2026-10-14 23:32:37'),(3,'Carlos Eduardo','Ruiz','Martínez','2026-10-14 23:32:37'),(4,'María Fernanda','Torres','Silva','2026-10-14 23:32:37'),(5,'Jorge','Hernández','Díaz','2026-10-14 23:32:37');
/*!40000 ALTER TABLE `persona` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol` (
  `IdRol` int NOT NULL AUTO_INCREMENT,
  `NombreRol` enum('ALUMNO','DOCENTE','SUPER_GESTOR') COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`IdRol`),
  UNIQUE KEY `NombreRol` (`NombreRol`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES (1,'ALUMNO'),(2,'DOCENTE'),(3,'SUPER_GESTOR');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `IdUsuario` int NOT NULL AUTO_INCREMENT,
  `IdPersona` int NOT NULL,
  `IdRol` int NOT NULL,
  `Username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `PasswordHash` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Activo` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`IdUsuario`),
  UNIQUE KEY `Username` (`Username`),
  KEY `IdPersona` (`IdPersona`),
  KEY `IdRol` (`IdRol`),
  CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`IdPersona`) REFERENCES `persona` (`IdPersona`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `usuario_ibfk_2` FOREIGN KEY (`IdRol`) REFERENCES `rol` (`IdRol`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,1,3,'gustavo.admin','$2a$10$tNHZg4AqVhvmu5qqzMiqhOspBRS0rmluqZvI64ii6i1N.F4dnKf3O',1),(2,2,1,'ana.alumno','$2a$10$tNHZg4AqVhvmu5qqzMiqhOspBRS0rmluqZvI64ii6i1N.F4dnKf3O',1),(3,3,2,'carlos.docente','$2a$10$tNHZg4AqVhvmu5qqzMiqhOspBRS0rmluqZvI64ii6i1N.F4dnKf3O',1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-11-10 22:19:47
