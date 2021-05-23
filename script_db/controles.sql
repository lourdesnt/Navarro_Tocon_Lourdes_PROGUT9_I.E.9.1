-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: controles
-- ------------------------------------------------------
-- Server version	8.0.23

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
  `num_matricula` int unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `grupo` int NOT NULL,
  PRIMARY KEY (`num_matricula`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alumno`
--

LOCK TABLES `alumno` WRITE;
/*!40000 ALTER TABLE `alumno` DISABLE KEYS */;
INSERT INTO `alumno` VALUES (1,'Lourdes Navarro',1),(2,'Laura Gomez',4),(3,'Jose Rodriguez',5),(4,'Ana Sanchez',4),(5,'Pablo Fernandez',3),(6,'Maria Lopez',2),(7,'Eduardo Torres',1),(8,'Clara Vera',2),(9,'Antonio Torres',6),(10,'Sara Romero',7);
/*!40000 ALTER TABLE `alumno` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `control_escrito`
--

DROP TABLE IF EXISTS `control_escrito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `control_escrito` (
  `codigo` int unsigned NOT NULL AUTO_INCREMENT,
  `num_preguntas` int unsigned DEFAULT NULL,
  `fecha` date NOT NULL,
  PRIMARY KEY (`codigo`),
  CONSTRAINT `control_escrito_chk_1` CHECK ((`num_preguntas` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `control_escrito`
--

LOCK TABLES `control_escrito` WRITE;
/*!40000 ALTER TABLE `control_escrito` DISABLE KEYS */;
INSERT INTO `control_escrito` VALUES (1,10,'2021-04-26'),(2,8,'2021-03-14'),(3,5,'2021-01-30'),(4,20,'2021-05-24'),(5,12,'2021-06-01'),(6,15,'2021-01-22');
/*!40000 ALTER TABLE `control_escrito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `disena`
--

DROP TABLE IF EXISTS `disena`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `disena` (
  `dni_prof` varchar(9) NOT NULL,
  `cod_pract` int unsigned NOT NULL,
  `fecha` date NOT NULL,
  PRIMARY KEY (`dni_prof`,`cod_pract`),
  KEY `disena_ibfk_2` (`cod_pract`),
  CONSTRAINT `disena_ibfk_1` FOREIGN KEY (`dni_prof`) REFERENCES `profesor` (`dni`) ON DELETE CASCADE,
  CONSTRAINT `disena_ibfk_2` FOREIGN KEY (`cod_pract`) REFERENCES `practica` (`codigo`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `disena`
--

LOCK TABLES `disena` WRITE;
/*!40000 ALTER TABLE `disena` DISABLE KEYS */;
INSERT INTO `disena` VALUES ('21111111A',1,'2021-04-14'),('21111111A',2,'2021-02-14'),('22222222B',2,'2021-02-14'),('22222222B',4,'2021-03-15'),('23333333C',3,'2021-06-06'),('23333333C',5,'2021-01-29'),('24444444D',2,'2021-02-14'),('24444444D',3,'2021-06-06'),('25555555E',4,'2021-03-15');
/*!40000 ALTER TABLE `disena` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `practica`
--

DROP TABLE IF EXISTS `practica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `practica` (
  `codigo` int unsigned NOT NULL AUTO_INCREMENT,
  `titulo` varchar(100) NOT NULL,
  `dificultad` enum('Baja','Media','Alta') NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `practica`
--

LOCK TABLES `practica` WRITE;
/*!40000 ALTER TABLE `practica` DISABLE KEYS */;
INSERT INTO `practica` VALUES (1,'Pendulo','Baja'),(2,'Densidad','Alta'),(3,'Magnetismo','Media'),(4,'Radiacion','Media'),(5,'Optica','Baja');
/*!40000 ALTER TABLE `practica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profesor`
--

DROP TABLE IF EXISTS `profesor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profesor` (
  `dni` varchar(9) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profesor`
--

LOCK TABLES `profesor` WRITE;
/*!40000 ALTER TABLE `profesor` DISABLE KEYS */;
INSERT INTO `profesor` VALUES ('21111111A','Manuel Fernandez'),('22222222B','Juan Madrid'),('23333333C','Teresa Montes'),('24444444D','Carolina Segovia'),('25555555E','David Oviedo');
/*!40000 ALTER TABLE `profesor` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`lnavtoc`@`localhost`*/ /*!50003 TRIGGER `dni_valido` BEFORE INSERT ON `profesor` FOR EACH ROW BEGIN
	DECLARE error_formato_dni CONDITION FOR SQLSTATE '70003';
	IF verificaDNI(NEW.dni) = FALSE THEN
		SIGNAL error_formato_dni
		SET MESSAGE_TEXT = 'El DNI introducido no es valido',
		MYSQL_ERRNO = 7003;
	END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `realiza_control`
--

DROP TABLE IF EXISTS `realiza_control`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `realiza_control` (
  `matric_alumno` int unsigned NOT NULL,
  `cod_control` int unsigned NOT NULL,
  `nota` float(4,2) NOT NULL,
  PRIMARY KEY (`matric_alumno`,`cod_control`),
  KEY `realiza_control_ibfk_2` (`cod_control`),
  CONSTRAINT `realiza_control_ibfk_1` FOREIGN KEY (`matric_alumno`) REFERENCES `alumno` (`num_matricula`) ON DELETE CASCADE,
  CONSTRAINT `realiza_control_ibfk_2` FOREIGN KEY (`cod_control`) REFERENCES `control_escrito` (`codigo`) ON DELETE CASCADE,
  CONSTRAINT `realiza_control_chk_1` CHECK (((`nota` >= 0) and (`nota` <= 10)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `realiza_control`
--

LOCK TABLES `realiza_control` WRITE;
/*!40000 ALTER TABLE `realiza_control` DISABLE KEYS */;
INSERT INTO `realiza_control` VALUES (1,1,10.00),(2,2,5.00),(3,2,6.50),(4,2,8.00),(5,3,9.00),(6,3,3.00),(7,4,2.00),(8,4,3.50),(9,5,7.00),(10,5,10.00);
/*!40000 ALTER TABLE `realiza_control` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `realiza_practica`
--

DROP TABLE IF EXISTS `realiza_practica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `realiza_practica` (
  `matric_alumno` int unsigned NOT NULL,
  `cod_pract` int unsigned NOT NULL,
  `fecha` date NOT NULL,
  `nota` float(4,2) NOT NULL,
  PRIMARY KEY (`matric_alumno`,`cod_pract`),
  KEY `realiza_practica_ibfk_2` (`cod_pract`),
  CONSTRAINT `realiza_practica_ibfk_1` FOREIGN KEY (`matric_alumno`) REFERENCES `alumno` (`num_matricula`) ON DELETE CASCADE,
  CONSTRAINT `realiza_practica_ibfk_2` FOREIGN KEY (`cod_pract`) REFERENCES `practica` (`codigo`) ON DELETE CASCADE,
  CONSTRAINT `realiza_practica_chk_1` CHECK (((`nota` >= 0) and (`nota` <= 10)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `realiza_practica`
--

LOCK TABLES `realiza_practica` WRITE;
/*!40000 ALTER TABLE `realiza_practica` DISABLE KEYS */;
INSERT INTO `realiza_practica` VALUES (1,5,'2021-01-30',7.00),(2,5,'2021-01-31',5.50),(3,4,'2021-03-17',3.00),(4,4,'2021-03-16',4.50),(5,2,'2021-02-15',9.00),(6,1,'2021-04-16',10.00),(7,2,'2021-02-15',9.00),(8,3,'2021-06-07',8.00),(9,3,'2021-06-07',8.00),(10,1,'2021-04-15',6.00);
/*!40000 ALTER TABLE `realiza_practica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'controles'
--

--
-- Dumping routines for database 'controles'
--
/*!50003 DROP FUNCTION IF EXISTS `verificaDNI` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`lnavtoc`@`localhost` FUNCTION `verificaDNI`( cadena VARCHAR (9)) RETURNS tinyint(1)
    DETERMINISTIC
BEGIN
	DECLARE patron VARCHAR (40);
	SET patron = "^[0-9]{8}[A-Z]{1}$";
	IF cadena REGEXP patron THEN
		RETURN TRUE;
	ELSE
		RETURN FALSE;
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `obtenerDatosAlumno` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`lnavtoc`@`localhost` PROCEDURE `obtenerDatosAlumno`(num_matric INT)
BEGIN
	SELECT * FROM alumno WHERE num_matricula = num_matric;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-23 18:55:47
