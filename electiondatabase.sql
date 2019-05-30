-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: electiondatabase
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `constituency`
--

DROP TABLE IF EXISTS `constituency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `constituency` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `state` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `constituency`
--

LOCK TABLES `constituency` WRITE;
/*!40000 ALTER TABLE `constituency` DISABLE KEYS */;
INSERT INTO `constituency` VALUES (1,'dholpur','rajasthan'),(2,'bhopal','madhyapradesh'),(3,'pune','maharashtra'),(4,'nagpur','maharashtra'),(5,'bangalore','andhra pradesh');
/*!40000 ALTER TABLE `constituency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `politicalparty`
--

DROP TABLE IF EXISTS `politicalparty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `politicalparty` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `symbol` varchar(500) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `symbol_UNIQUE` (`symbol`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `politicalparty`
--

LOCK TABLES `politicalparty` WRITE;
/*!40000 ALTER TABLE `politicalparty` DISABLE KEYS */;
INSERT INTO `politicalparty` VALUES (1,'bjp','lotus'),(2,'congress','hand'),(3,'bsp','elephant');
/*!40000 ALTER TABLE `politicalparty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vote`
--

DROP TABLE IF EXISTS `vote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `vote` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `voterId` int(11) NOT NULL,
  `politicalPartyId` int(11) NOT NULL,
  `constituencyId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `voterId_UNIQUE` (`voterId`),
  KEY `voterId_idx` (`voterId`),
  KEY `politicalpartykey_idx` (`politicalPartyId`),
  KEY `constituenyKey_idx` (`constituencyId`),
  CONSTRAINT `consituencykey` FOREIGN KEY (`constituencyId`) REFERENCES `constituency` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `politicalpartykey` FOREIGN KEY (`politicalPartyId`) REFERENCES `politicalparty` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `voterkey` FOREIGN KEY (`voterId`) REFERENCES `voter` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vote`
--

LOCK TABLES `vote` WRITE;
/*!40000 ALTER TABLE `vote` DISABLE KEYS */;
INSERT INTO `vote` VALUES (4,4,2,2),(5,5,2,2),(6,7,2,3),(7,6,1,2),(8,1,2,1),(9,8,1,4),(10,9,2,4),(11,10,1,5),(12,11,1,5),(13,12,2,5);
/*!40000 ALTER TABLE `vote` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voter`
--

DROP TABLE IF EXISTS `voter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `voter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uniqueId` varchar(200) NOT NULL,
  `name` varchar(200) NOT NULL,
  `constituencyId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueId_UNIQUE` (`uniqueId`),
  KEY `cons_idx` (`constituencyId`),
  CONSTRAINT `constituencyId` FOREIGN KEY (`constituencyId`) REFERENCES `constituency` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voter`
--

LOCK TABLES `voter` WRITE;
/*!40000 ALTER TABLE `voter` DISABLE KEYS */;
INSERT INTO `voter` VALUES (1,'uid1','rohit',1),(2,'uid2','chirag',1),(3,'uid3','anand',1),(4,'uid4','parul',2),(5,'uid5','akhil',2),(6,'uid6','anil',2),(7,'uid7','shetal',3),(8,'uid8','paras',4),(9,'uid9','utpal',4),(10,'uid10','anup',5),(11,'uid11','parag',5),(12,'uid12','zen',5),(18,'uid24','rohit',1);
/*!40000 ALTER TABLE `voter` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-30 14:30:12
