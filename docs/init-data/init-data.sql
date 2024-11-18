-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: demo_jpa
-- ------------------------------------------------------
-- Server version	5.5.5-10.6.18-MariaDB-0ubuntu0.22.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `id_menu` varchar(255) NOT NULL,
  `display_name` varchar(255) DEFAULT NULL,
  `fg_active` bit(1) DEFAULT NULL,
  `fg_show` bit(1) DEFAULT NULL,
  `id_parent` varchar(255) DEFAULT NULL,
  `menu_type` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `query` varchar(255) DEFAULT NULL,
  `service_perms` varchar(255) DEFAULT NULL,
  `web_perms` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_menu`),
  KEY `FKky4ikkiig2500xs8era5pmyff` (`id_parent`),
  CONSTRAINT `FKky4ikkiig2500xs8era5pmyff` FOREIGN KEY (`id_parent`) REFERENCES `sys_menu` (`id_menu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `id_role` varchar(255) NOT NULL,
  `display_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu` (
  `id_role_menu` varchar(255) NOT NULL,
  `id_menu` varchar(255) DEFAULT NULL,
  `id_role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_role_menu`),
  KEY `FK9patcirm7na1t5h2ydkrdtmbg` (`id_menu`),
  KEY `FKn2k7u0uuwp6o3p4i6oci1cjnt` (`id_role`),
  CONSTRAINT `FK9patcirm7na1t5h2ydkrdtmbg` FOREIGN KEY (`id_menu`) REFERENCES `sys_menu` (`id_menu`),
  CONSTRAINT `FKn2k7u0uuwp6o3p4i6oci1cjnt` FOREIGN KEY (`id_role`) REFERENCES `sys_role` (`id_role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_token`
--

DROP TABLE IF EXISTS `sys_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_token` (
  `id_sys_token` varchar(255) NOT NULL,
  `create_time` date DEFAULT NULL,
  `expired_time` date DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `token` varchar(4000) DEFAULT NULL,
  `user_info_string` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_sys_token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_token`
--

LOCK TABLES `sys_token` WRITE;
/*!40000 ALTER TABLE `sys_token` DISABLE KEYS */;
INSERT INTO `sys_token` VALUES ('0000-98b9e583-878e-4852-824a-0a8f57f00706','2024-11-18','2024-11-25','超级管理员','eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ0Y2R0IiwiY3JlYXRlZCI6MTczMTg5MjM5OTM0NCwiZXhwIjoxNzMyNDk3MTk5fQ.hZSBYcuzEdVt2RjYH2HPaIqUCtqjCi1Yrp68tvnCxrD_2XiS2dxWzGqJHIsJ0Mg_nRd8JT74EqA18dQLLfcAjPjqRjdGPlGKngrjMsKuGad-nvOcfYsnnYMjRVo0MK_uJMwasKfHt7WLRdkJW21LoNLECU_eXjbgkqoaeRM2uSGl6eLkAzIxEx1h9f5rCJVuYppfJ2AIG6_cBnLogCofkdkfUsmJ8Z8o2v__3OFt_PQa8fEgN4wj4j1IthgSH0VibCwoBHFTAZ39wh1S1elEwbO_xPqjuIjYPRW9VK-I65ypuQNz0qRLig8fFSUGhYjDRPiXivZ-PqATGOR2bBXPrA','{\"username\":\"tcdt\",\"password\":null,\"fgActive\":true,\"nickName\":\"超级管理员\",\"token\":null,\"createTime\":1731892399344,\"expiredTime\":1732497199344}','tcdt');
/*!40000 ALTER TABLE `sys_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `id_user` varchar(255) NOT NULL,
  `account` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fg_active` bit(1) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `user_pwd` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES ('0000-1d2816c9-6935-466a-bd54-85c4de936785','tcdt','y102715@100086.com',_binary '','male','超级管理员','超级管理员','100086','$2a$10$Vud.FS2j2lMzXDMTguwDi.QGG3nv0zpeOYyNBO9UYEJLngXNiV9V.');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `id_sys_user_role` varchar(255) NOT NULL,
  `id_role` varchar(255) DEFAULT NULL,
  `id_user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_sys_user_role`),
  KEY `FKppxltsh6dc1wjycy77mwlmpkg` (`id_role`),
  KEY `FKsdevss9qnfx49a7a8igyrywlc` (`id_user`),
  CONSTRAINT `FKppxltsh6dc1wjycy77mwlmpkg` FOREIGN KEY (`id_role`) REFERENCES `sys_role` (`id_role`),
  CONSTRAINT `FKsdevss9qnfx49a7a8igyrywlc` FOREIGN KEY (`id_user`) REFERENCES `sys_user` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'demo_jpa'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-18 20:08:08
