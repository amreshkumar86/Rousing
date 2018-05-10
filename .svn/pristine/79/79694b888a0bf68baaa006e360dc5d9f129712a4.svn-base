-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: oen_repo
-- ------------------------------------------------------
-- Server version	5.7.16-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `attribute_master`
--

DROP TABLE IF EXISTS `attribute_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attribute_master` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `record_status` int(2) NOT NULL,
  `update_on` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attribute_master`
--

LOCK TABLES `attribute_master` WRITE;
/*!40000 ALTER TABLE `attribute_master` DISABLE KEYS */;
INSERT INTO `attribute_master` VALUES (1,'POWER',1,'2017-06-05 18:17:46'),(2,'BRIGHTNESS',1,'2017-06-05 18:17:46');
/*!40000 ALTER TABLE `attribute_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `contact_number` varchar(15) NOT NULL,
  `registered_on` datetime NOT NULL,
  `description` varchar(100) NOT NULL,
  `record_status` int(2) NOT NULL,
  `update_on` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'NIVEUS','1234567890','2017-06-05 18:17:46','Customer who is located in udupi, karnataka.',1,'2017-06-05 18:17:46');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_items`
--

DROP TABLE IF EXISTS `customer_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_items` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_id` bigint(20) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `manufacture_id` bigint(20) NOT NULL,
  `item_unique_number` varchar(50) NOT NULL,
  `item_batch_number` varchar(100) NOT NULL,
  `item_nickname` varchar(20) NOT NULL,
  `record_status` int(2) NOT NULL,
  `update_on` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_items_fk0` (`customer_id`),
  KEY `customer_items_fk2` (`manufacture_id`),
  KEY `customer_items_fk1` (`item_id`),
  CONSTRAINT `customer_items_fk0` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `customer_items_fk1` FOREIGN KEY (`item_id`) REFERENCES `item_master` (`id`),
  CONSTRAINT `customer_items_fk2` FOREIGN KEY (`manufacture_id`) REFERENCES `manufacturer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_items`
--

LOCK TABLES `customer_items` WRITE;
/*!40000 ALTER TABLE `customer_items` DISABLE KEYS */;
INSERT INTO `customer_items` VALUES (1,1,1,1,'123456','98765432','Coriidor Bulb',1,'2017-06-05 18:17:46'),(2,1,1,1,'654321','34567892','Staircase Bulb',1,'2017-06-05 18:17:46');
/*!40000 ALTER TABLE `customer_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favourite_attribute_mapping`
--

DROP TABLE IF EXISTS `favourite_attribute_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `favourite_attribute_mapping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `favuorite_master_id` bigint(20) NOT NULL,
  `attribute_master_id` bigint(20) NOT NULL,
  `input_value` bigint(20) NOT NULL,
  `record_status` int(2) NOT NULL,
  `update_on` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `favourite_attribute_mapping_fk0` (`favuorite_master_id`),
  KEY `favourite_attribute_mapping_fk1` (`attribute_master_id`),
  CONSTRAINT `favourite_attribute_mapping_fk0` FOREIGN KEY (`favuorite_master_id`) REFERENCES `favourite_master` (`id`),
  CONSTRAINT `favourite_attribute_mapping_fk1` FOREIGN KEY (`attribute_master_id`) REFERENCES `attribute_master` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favourite_attribute_mapping`
--

LOCK TABLES `favourite_attribute_mapping` WRITE;
/*!40000 ALTER TABLE `favourite_attribute_mapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `favourite_attribute_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favourite_master`
--

DROP TABLE IF EXISTS `favourite_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `favourite_master` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `created_by` bigint(20) NOT NULL,
  `record_status` int(2) NOT NULL,
  `update_on` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `favourite_master_fk0` (`created_by`),
  CONSTRAINT `favourite_master_fk0` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favourite_master`
--

LOCK TABLES `favourite_master` WRITE;
/*!40000 ALTER TABLE `favourite_master` DISABLE KEYS */;
/*!40000 ALTER TABLE `favourite_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_items`
--

DROP TABLE IF EXISTS `group_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_items` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_item_id` bigint(20) NOT NULL,
  `group_master_id` bigint(20) NOT NULL,
  `record_status` int(2) NOT NULL,
  `update_on` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `group_items_fk0` (`customer_item_id`),
  KEY `group_items_fk1` (`group_master_id`),
  CONSTRAINT `group_items_fk0` FOREIGN KEY (`customer_item_id`) REFERENCES `customer_items` (`id`),
  CONSTRAINT `group_items_fk1` FOREIGN KEY (`group_master_id`) REFERENCES `group_master` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_items`
--

LOCK TABLES `group_items` WRITE;
/*!40000 ALTER TABLE `group_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_master`
--

DROP TABLE IF EXISTS `group_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_master` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` bigint(20) NOT NULL,
  `group_name` varchar(20) NOT NULL,
  `created_on` datetime NOT NULL,
  `record_status` int(2) NOT NULL,
  `update_on` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `group_master_fk0` (`created_by`),
  CONSTRAINT `group_master_fk0` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_master`
--

LOCK TABLES `group_master` WRITE;
/*!40000 ALTER TABLE `group_master` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_attribute_history`
--

DROP TABLE IF EXISTS `item_attribute_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_attribute_history` (
  `id` int(11) NOT NULL,
  `customer_item_id` bigint(20) NOT NULL,
  `item_attribute_id` bigint(20) NOT NULL,
  `changed_by` bigint(20) NOT NULL,
  `attribute_value` int(3) NOT NULL,
  `record_status` int(2) NOT NULL,
  `update_on` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `item_attribute_history_fk0` (`customer_item_id`),
  KEY `item_attribute_history_fk1` (`item_attribute_id`),
  KEY `item_attribute_history_fk2` (`changed_by`),
  CONSTRAINT `item_attribute_history_fk0` FOREIGN KEY (`customer_item_id`) REFERENCES `customer_items` (`id`),
  CONSTRAINT `item_attribute_history_fk1` FOREIGN KEY (`item_attribute_id`) REFERENCES `item_attributes` (`id`),
  CONSTRAINT `item_attribute_history_fk2` FOREIGN KEY (`changed_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_attribute_history`
--

LOCK TABLES `item_attribute_history` WRITE;
/*!40000 ALTER TABLE `item_attribute_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `item_attribute_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_attribute_schedule`
--

DROP TABLE IF EXISTS `item_attribute_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_attribute_schedule` (
  `id` int(11) NOT NULL,
  `customer_item_id` bigint(20) NOT NULL,
  `schedule_time` datetime NOT NULL,
  `timer_value` varchar(20) NOT NULL,
  `timer_executed` tinyint(1) NOT NULL,
  `record_status` int(2) NOT NULL,
  `update_on` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `item_attribute_schedule_fk0` (`customer_item_id`),
  CONSTRAINT `item_attribute_schedule_fk0` FOREIGN KEY (`customer_item_id`) REFERENCES `customer_items` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_attribute_schedule`
--

LOCK TABLES `item_attribute_schedule` WRITE;
/*!40000 ALTER TABLE `item_attribute_schedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `item_attribute_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_attribute_state`
--

DROP TABLE IF EXISTS `item_attribute_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_attribute_state` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_item_id` bigint(20) NOT NULL,
  `attributes_master_id` bigint(20) NOT NULL,
  `attribute_value` bigint(20) NOT NULL,
  `record_status` int(2) NOT NULL,
  `update_on` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_attribute_details_fk0` (`customer_item_id`),
  KEY `customer_attribute_details_fk1` (`attributes_master_id`),
  CONSTRAINT `customer_attribute_details_fk0` FOREIGN KEY (`customer_item_id`) REFERENCES `customer_items` (`id`),
  CONSTRAINT `customer_attribute_details_fk1` FOREIGN KEY (`attributes_master_id`) REFERENCES `attribute_master` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_attribute_state`
--

LOCK TABLES `item_attribute_state` WRITE;
/*!40000 ALTER TABLE `item_attribute_state` DISABLE KEYS */;
/*!40000 ALTER TABLE `item_attribute_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_attributes`
--

DROP TABLE IF EXISTS `item_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_attributes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `item_id` bigint(20) NOT NULL,
  `attribute_master_id` bigint(20) NOT NULL,
  `minimum_input_value` int(3) NOT NULL,
  `maximum_input_value` int(3) NOT NULL,
  `record_status` int(2) NOT NULL,
  `update_on` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `item_attributes_fk0` (`item_id`),
  KEY `item_attributes_fk1` (`attribute_master_id`),
  CONSTRAINT `item_attributes_fk0` FOREIGN KEY (`item_id`) REFERENCES `item_master` (`id`),
  CONSTRAINT `item_attributes_fk1` FOREIGN KEY (`attribute_master_id`) REFERENCES `attribute_master` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_attributes`
--

LOCK TABLES `item_attributes` WRITE;
/*!40000 ALTER TABLE `item_attributes` DISABLE KEYS */;
INSERT INTO `item_attributes` VALUES (1,1,1,0,1,1,'2017-06-05 18:17:46'),(2,1,2,0,255,1,'2017-06-05 18:17:46');
/*!40000 ALTER TABLE `item_attributes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_master`
--

DROP TABLE IF EXISTS `item_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_master` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `record_status` int(2) NOT NULL,
  `update_on` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_master`
--

LOCK TABLES `item_master` WRITE;
/*!40000 ALTER TABLE `item_master` DISABLE KEYS */;
INSERT INTO `item_master` VALUES (1,'BULB',1,'2017-06-05 18:17:46');
/*!40000 ALTER TABLE `item_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manufacturer`
--

DROP TABLE IF EXISTS `manufacturer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manufacturer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `server_url` varchar(100) NOT NULL,
  `alternate_server_url` varchar(100) DEFAULT NULL,
  `auth_uname` varchar(50) NOT NULL,
  `auth_password` varchar(50) NOT NULL,
  `record_status` int(2) NOT NULL,
  `update_on` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manufacturer`
--

LOCK TABLES `manufacturer` WRITE;
/*!40000 ALTER TABLE `manufacturer` DISABLE KEYS */;
INSERT INTO `manufacturer` VALUES (1,'REDMI','http://www.jhdgh.com/dbjjh/dgjgj','http://www.jhdgh.com/dbjjh/dgjgj','qwerty','1234567890',1,'2017-06-05 18:17:46');
/*!40000 ALTER TABLE `manufacturer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preset_attribute_mapping`
--

DROP TABLE IF EXISTS `preset_attribute_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `preset_attribute_mapping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `preset_master_id` bigint(20) NOT NULL,
  `attribute_master_id` bigint(20) NOT NULL,
  `input_value` bigint(20) NOT NULL,
  `record_status` int(2) NOT NULL,
  `update_on` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `preset_attribute_mapping_fk0` (`preset_master_id`),
  KEY `preset_attribute_mapping_fk1` (`attribute_master_id`),
  CONSTRAINT `preset_attribute_mapping_fk0` FOREIGN KEY (`preset_master_id`) REFERENCES `preset_master` (`id`),
  CONSTRAINT `preset_attribute_mapping_fk1` FOREIGN KEY (`attribute_master_id`) REFERENCES `attribute_master` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preset_attribute_mapping`
--

LOCK TABLES `preset_attribute_mapping` WRITE;
/*!40000 ALTER TABLE `preset_attribute_mapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `preset_attribute_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preset_master`
--

DROP TABLE IF EXISTS `preset_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `preset_master` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `record_status` int(2) NOT NULL,
  `update_on` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preset_master`
--

LOCK TABLES `preset_master` WRITE;
/*!40000 ALTER TABLE `preset_master` DISABLE KEYS */;
/*!40000 ALTER TABLE `preset_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `record_status` int(11) NOT NULL,
  `update_on` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_ADMIN',1,'2017-03-18 17:38:31'),(2,'ROLE_USER',1,'2017-03-18 17:38:31');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_id` bigint(20) DEFAULT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `phone_number` varchar(50) DEFAULT NULL,
  `password` varchar(100) NOT NULL,
  `registered_on` datetime DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `record_status` int(2) NOT NULL,
  `update_on` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_user_customer_id_idx` (`customer_id`),
  CONSTRAINT `fk_user_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,1,'Smart','Lighting','smartlight@test.com','smartlight@test.com','8749006584','$2a$10$/KyRTLAYcFLe6zykzWyJkuxaFLR5D0jHsnZW9TT4Thp2thypxbpJ2','2017-04-29 18:17:46','',1,'2017-04-29 18:17:46'),(5,1,'ABCC','DEFF','hjh@nhjk.com','hjh@nhjk.com','1234567890','$2a$10$5R1UCnAlK8.mZynWDcgWAegl0K5Y8Whk2Gqf3A2UTCvoGAQIFY90.','2017-05-03 11:26:08','',1,'2017-05-03 11:26:08'),(9,1,'fsdfs','dsfsdf','mnb@mnb.com','mnb@mnb.com','1234567890','$2a$10$WDgIzYky2cKHNBOYZHGJgepMqKFKpQ9X4VkVAd6UF4gVPypdjdFU.','2017-03-18 17:38:31','',1,'2017-03-18 17:38:31');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_feedback`
--

DROP TABLE IF EXISTS `user_feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_feedback` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `feedback_by` bigint(20) NOT NULL,
  `title` varchar(20) NOT NULL,
  `query` text NOT NULL,
  `given_on` datetime NOT NULL,
  `status` int(2) NOT NULL,
  `record_status` int(2) NOT NULL,
  `update_on` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_feedback_fk0` (`feedback_by`),
  CONSTRAINT `user_feedback_fk0` FOREIGN KEY (`feedback_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_feedback`
--

LOCK TABLES `user_feedback` WRITE;
/*!40000 ALTER TABLE `user_feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `record_status` int(11) NOT NULL,
  `update_on` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_role_role_id` (`role_id`),
  KEY `fk_user_role_user_id` (`user_id`),
  CONSTRAINT `fk_user_role_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `fk_user_role_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,5,2,1,'2017-05-03 11:26:08');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'oen_repo'
--

--
-- Dumping routines for database 'oen_repo'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-17 12:04:52
