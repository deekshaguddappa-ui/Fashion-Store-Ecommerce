-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: fashion
-- ------------------------------------------------------
-- Server version	8.0.45

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
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `cart_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`cart_id`),
  UNIQUE KEY `user_id` (`user_id`),
  CONSTRAINT `fk_cart_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (1,1,'2026-05-03 07:25:15'),(2,2,'2026-05-03 07:25:15'),(3,4,'2026-05-04 09:19:39');
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items` (
  `cart_item_id` int NOT NULL AUTO_INCREMENT,
  `cart_id` int NOT NULL,
  `variant_id` int NOT NULL,
  `quantity` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`cart_item_id`),
  UNIQUE KEY `uq_cart_variant` (`cart_id`,`variant_id`),
  KEY `fk_cartitems_variant` (`variant_id`),
  CONSTRAINT `fk_cartitems_cart` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`cart_id`),
  CONSTRAINT `fk_cartitems_variant` FOREIGN KEY (`variant_id`) REFERENCES `product_variants` (`variant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
INSERT INTO `cart_items` VALUES (1,1,1,2),(2,1,6,1),(3,2,12,1);
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(50) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `category_name` (`category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Men','Fashion products for men'),(2,'Women','Fashion products for women'),(3,'Kids','Fashion products for kids'),(4,'Accessories','Fashion accessories'),(5,'Footwear','Shoes and sandals');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `order_item_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `variant_id` int NOT NULL,
  `quantity` int NOT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`order_item_id`),
  KEY `fk_orderitems_order` (`order_id`),
  KEY `fk_orderitems_variant` (`variant_id`),
  CONSTRAINT `fk_orderitems_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `fk_orderitems_variant` FOREIGN KEY (`variant_id`) REFERENCES `product_variants` (`variant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (1,1,1,2,1299.00),(2,1,6,1,2299.00),(3,2,8,1,1799.00),(11,3,23,1,1899.00),(12,4,20,1,599.00),(13,5,19,1,2299.00),(14,5,20,1,599.00),(15,6,22,1,1899.00),(16,7,7,1,2299.00),(17,7,2,2,1299.00),(18,8,5,1,999.00),(19,9,6,2,2299.00),(20,10,5,3,999.00),(21,11,6,1,2299.00),(22,12,4,1,999.00),(23,13,30,1,1999.00),(24,14,30,1,1999.00),(25,15,4,1,999.00),(26,16,7,1,2299.00),(27,17,6,1,2299.00);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `order_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `total_amount` decimal(10,2) NOT NULL,
  `payment_method` varchar(50) NOT NULL,
  `order_status` varchar(50) NOT NULL DEFAULT 'PLACED',
  `delivery_name` varchar(100) NOT NULL,
  `delivery_phone` varchar(15) NOT NULL,
  `delivery_address_line1` varchar(150) NOT NULL,
  `delivery_address_line2` varchar(150) DEFAULT NULL,
  `delivery_city` varchar(100) NOT NULL,
  `delivery_state` varchar(100) NOT NULL,
  `delivery_pincode` varchar(10) NOT NULL,
  `delivery_country` varchar(100) NOT NULL DEFAULT 'India',
  PRIMARY KEY (`order_id`),
  KEY `fk_orders_user` (`user_id`),
  CONSTRAINT `fk_orders_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1,'2026-05-03 07:25:30',2598.00,'UPI','PLACED','Somanna MG','9876543210','1st Main Road','BTM Layout','Bengaluru','Karnataka','560076','India'),(2,2,'2026-05-03 07:25:30',1799.00,'COD','PLACED','Ananya Rao','9876501234','44 MG Road','Near Metro Station','Bengaluru','Karnataka','560001','India'),(3,4,'2026-05-05 05:31:10',1899.00,'COD','PLACED','DEEKSHA G','5346342432','dfgf','','Shivamogga','Karnataka','577419','India'),(4,4,'2026-05-05 05:40:56',599.00,'COD','PLACED','DEEKSHA G','5346342432','xfgdf','','Shivamogga','Karnataka','577419','India'),(5,4,'2026-05-05 05:42:14',2898.00,'COD','PLACED','DEEKSHA G','5346342432','dsf','','Shivamogga','Karnataka','577419','India'),(6,4,'2026-05-05 05:54:14',1899.00,'COD','PLACED','DEEKSHA G','5346342432','fds','','Shivamogga','Karnataka','577419','India'),(7,4,'2026-05-05 06:07:36',4897.00,'COD','PLACED','DEEKSHA G','5346342432','dvdd','','Shivamogga','Karnataka','577419','India'),(8,4,'2026-05-07 12:26:42',999.00,'COD','PLACED','DEEKSHA G','5346342432','adjbd','','Shivamogga','Karnataka','577419','India'),(9,4,'2026-05-08 04:16:08',4598.00,'COD','PLACED','DEEKSHA G','5346342432','vhvghcv','','Shivamogga','Karnataka','577419','India'),(10,4,'2026-05-08 05:30:05',2997.00,'COD','PLACED','DEEKSHA G','5346342432','nsdjashf','','Shivamogga','Karnataka','577419','India'),(11,4,'2026-05-08 13:27:39',2299.00,'COD','PLACED','DEEKSHA G','5346342432',' fddgdf','','Shivamogga','Karnataka','577419','India'),(12,4,'2026-05-10 08:51:26',999.00,'COD','PLACED','DEEKSHA G','5346342432','dffds','','Shivamogga','Karnataka','577419','India'),(13,4,'2026-05-11 10:06:40',1999.00,'COD','PLACED','DEEKSHA G','5346342432','Deeksha D/O Guddappa, Shiddihalli village soraba tq','','Shivamogga','Karnataka','577419','India'),(14,4,'2026-05-11 10:12:18',1999.00,'COD','PLACED','DEEKSHA G','5346342432','Deeksha D/O Guddappa, Shiddihalli village soraba tq','','Shivamogga','Karnataka','577419','India'),(15,4,'2026-05-14 04:26:16',999.00,'COD','PLACED','DEEKSHA G','5346342432','cfghgfhjgfghd','','Shivamogga','Karnataka','577419','India'),(16,4,'2026-05-14 04:43:49',2299.00,'COD','PLACED','DEEKSHA G','5346342432','HGJ','','Shivamogga','Karnataka','577419','India'),(17,4,'2026-06-21 07:03:32',2299.00,'COD','PLACED','DEEKSHA G','5346342432',' jhch','','Shivamogga','Karnataka','577419','India');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_variants`
--

DROP TABLE IF EXISTS `product_variants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_variants` (
  `variant_id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `size` varchar(20) NOT NULL,
  `stock` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`variant_id`),
  KEY `fk_variants_product` (`product_id`),
  CONSTRAINT `fk_variants_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_variants`
--

LOCK TABLES `product_variants` WRITE;
/*!40000 ALTER TABLE `product_variants` DISABLE KEYS */;
INSERT INTO `product_variants` VALUES (1,1,'S',10),(2,1,'M',15),(3,1,'L',12),(4,3,'S',10),(5,3,'M',8),(6,2,'30',10),(7,2,'32',11),(8,2,'34',9),(9,6,'28',7),(10,6,'30',10),(11,6,'32',8),(12,7,'4-5Y',10),(13,7,'6-7Y',9),(14,7,'8-9Y',7),(15,8,'4-5Y',8),(16,8,'6-7Y',6),(17,8,'10-11Y',4),(18,9,'FREE',20),(19,10,'FREE',15),(20,11,'FREE',18),(21,12,'M',20),(22,12,'L',18),(23,12,'XL',12),(30,13,'M',10),(31,13,'L',8);
/*!40000 ALTER TABLE `product_variants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `category_id` int NOT NULL,
  `product_name` varchar(150) NOT NULL,
  `brand` varchar(100) DEFAULT NULL,
  `description` text NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `image_url` varchar(255) NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`product_id`),
  KEY `fk_products_category` (`category_id`),
  CONSTRAINT `fk_products_category` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,1,'Black Casual Shirt','Roadster','Men black casual cotton shirt',1299.00,'assets/images/products/men-shirt.jpg',1,'2026-05-03 07:24:27'),(2,1,'Blue Slim Fit Jeans','Levis','Men blue slim fit stretch jeans',2299.00,'assets/images/products/men-jeans.jpg',1,'2026-05-03 07:24:27'),(3,2,'White Polo T-Shirt','U.S Polo','Men white polo t-shirt with collar',999.00,'assets/images/products/polo.jpg',1,'2026-05-03 07:24:27'),(4,2,'Floral Summer Dress','Zara','Women floral printed summer dress',2499.00,'assets/images/products/dress.jpg',1,'2026-05-03 07:24:27'),(5,2,'Pink Kurti','Biba','Women pink straight kurti',1499.00,'assets/images/products/kurti.jpg',1,'2026-05-03 07:24:27'),(6,2,'Black High Waist Jeans','Only','Women black high waist jeans',2199.00,'assets/images/products/women-jeans.jpg',1,'2026-05-03 07:24:27'),(7,3,'Boys Checked Shirt','Max','Boys checked casual shirt',899.00,'assets/images/products/boys-shirt.jpg',1,'2026-05-03 07:24:27'),(8,3,'Girls Party Dress','Hopscotch','Girls party wear dress',1799.00,'assets/images/products/girls-dress.jpg',1,'2026-05-03 07:24:27'),(9,4,'Leather Belt','Allen Solly','Brown leather belt for men',799.00,'assets/images/products/belt.jpg',1,'2026-05-03 07:24:27'),(10,4,'Women Handbag','Caprese','Stylish handbag for women',2299.00,'assets/images/products/bag.jpg',1,'2026-05-03 07:24:27'),(11,4,'Unisex Cap','Puma','Adjustable unisex cap',599.00,'assets/images/products/cap.jpg',1,'2026-05-03 07:24:27'),(12,5,'Green Hoodie','H&M','Men green cotton hoodie',1899.00,'assets/images/products/hoodie.jpg',1,'2026-05-03 07:24:27'),(13,1,'White Hoodie','Nike','Men white cotton hoodie',1999.00,'assets/images/products/white-hoodie.jpg',1,'2026-05-11 09:50:09');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `password` varchar(255) NOT NULL,
  `address_line1` varchar(150) NOT NULL,
  `address_line2` varchar(150) DEFAULT NULL,
  `city` varchar(100) NOT NULL,
  `state` varchar(100) NOT NULL,
  `pincode` varchar(10) NOT NULL,
  `country` varchar(100) NOT NULL DEFAULT 'India',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Somanna MG','somanna@example.com','9876543210','somanna123','1st Main Road','BTM Layout','Bengaluru','Karnataka','560076','India','2026-05-03 07:25:11'),(2,'Ananya Rao','ananya@example.com','9876501234','ananya123','44 MG Road','Near Metro Station','Bengaluru','Karnataka','560001','India','2026-05-03 07:25:11'),(3,'DEEKSHA G','deekshaguddappa@gmail.com','05346342432','Deeksha@123','Deeksha D/O Guddappa, Shiddihalli village soraba tq','bangalore2','Shivamogga','Karnataka','577419','India','2026-05-04 08:23:36'),(4,'DEEKSHA G','pallaviguddappa@gmail.com','5346342432','Deeksha@22','Deeksha D/O Guddappa, Shiddihalli village soraba tq','bangalore2','Shivamogga','Karnataka','577419','India','2026-05-04 08:24:45');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-21 20:49:01
