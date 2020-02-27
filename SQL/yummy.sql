-- MySQL dump 10.13  Distrib 5.7.25, for Win64 (x86_64)
--
-- Host: localhost    Database: yummy
-- ------------------------------------------------------
-- Server version	5.7.25-log

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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `province` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `area` varchar(45) DEFAULT NULL,
  `detail` varchar(45) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `order` int(11) DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `lng` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'江苏','南京','鼓楼区','龙全花园 北京西路8号02幢',1,1,32.065605,118.7835),(2,'江苏','南京','鼓楼区','宁乐新苑 大方巷18号',2,1,32.071083,118.780754),(3,'北京','北京','西城区','西藏大厦 北京市朝阳区北四环东路118号(北四环安慧桥东)',1,2,39.9943,116.42033),(4,'内蒙古','呼和浩特','新城区','绿地腾飞大厦 呼和浩特市赛罕区腾飞路45号',1,3,40.82568,111.76556),(5,'江苏','南京','鼓楼区','紫峰大厦-观光层 中央路1号紫峰大厦72楼(近中山北路)',3,1,32.06676,118.78968);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `balance`
--

DROP TABLE IF EXISTS `balance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `balance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `balance` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `balance`
--

LOCK TABLES `balance` WRITE;
/*!40000 ALTER TABLE `balance` DISABLE KEYS */;
INSERT INTO `balance` VALUES (1,'zhenhuan','zhenhuan',1238.5900000000001),(2,'shilan','shilan',1136.6200000000001),(3,'yixiu','yixiu',1147.0149999999999),(4,'chunyuan','chunyuan',1072.27),(5,'meizhuang','meizhuang',1121.6512),(6,'qinmo','qinmo',1134.6399999999999),(7,'yunli','yunli',1642.1599999999999),(8,'shichu','shichu',1426.3799999999999),(9,'admin','admin',80.67380000000011);
/*!40000 ALTER TABLE `balance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `canteen`
--

DROP TABLE IF EXISTS `canteen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `canteen` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `hostName` varchar(45) DEFAULT NULL,
  `idNumber` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `cname` varchar(45) DEFAULT NULL,
  `caddress` varchar(45) DEFAULT NULL,
  `ctype` varchar(45) DEFAULT NULL,
  `cbegin` varchar(45) DEFAULT NULL,
  `cend` varchar(45) DEFAULT NULL,
  `check` varchar(45) DEFAULT NULL,
  `applyDate` varchar(45) DEFAULT NULL,
  `province` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `area` varchar(45) DEFAULT NULL,
  `account` varchar(45) DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `lng` double DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `canteen`
--

LOCK TABLES `canteen` WRITE;
/*!40000 ALTER TABLE `canteen` DISABLE KEYS */;
INSERT INTO `canteen` VALUES (1,'甄嬛','640102010101010101','wx8339459@163.com','zhenhuan','永寿宫','紫峰大厦停车场-入口 中央路1号紫峰购物广场B4','汉餐','09:00:00','21:00:00','pass','2019/03/18 23:01:37','江苏','南京','鼓楼区','zhenhuan',32.066982,118.789986),(2,'世兰','640102020202020202','wx8339459@163.com','shilan','翊坤宫','南京大学(鼓楼校区) 江苏省南京市鼓楼区汉口路22号','汉餐','09:00:00','21:00:00','pass','2019/03/18 23:04:45','江苏','南京','鼓楼区','shilan',32.06143,118.78601),(3,'宜修','640102030303030303','wx8339459@163.com','yixiu','景仁宫','南京鼓楼医院 南京市鼓楼区中山路321号','快餐','09:00:00','21:00:00','pass','2019/03/19 16:51:42','江苏','南京','鼓楼区','yixiu',32.06245,118.7896),(4,'纯元','640102040404040404','wx8339459@163.com','chunyuan','雍王府','云南路 江苏省南京市鼓楼区','西餐','09:00:00','21:00:00','pass','2019/03/19 17:00:49','江苏','南京','鼓楼区','chunyuan',32.067333,118.78108),(5,'眉庄','640102050505050505','wx8339459@163.com','meizhuang','存菊堂','江苏教育大厦 北京西路15号','西餐','09:00:00','21:00:00','pass','2019/03/19 17:02:01','江苏','南京','鼓楼区','meizhuang',32.064896,118.778656),(6,'琴默','640102060606060606','wx8339459@163.com','qinmo','延禧宫','南京大学附属中学 鼓楼街83号','其他','09:00:00','21:00:00','pass','2019/03/19 17:04:42','江苏','南京','鼓楼区','qinmo',32.067326,118.78668),(7,'王旭','640102199802010912','wx8339459@163.com','wangxu','王旭的餐厅','鼓楼公园 鼓楼区北京西路1-2号','西餐','09:00:00','21:00:00','pass','2019/03/22 10:04:21','江苏','南京','鼓楼区','yunli',32.064877,118.788475);
/*!40000 ALTER TABLE `canteen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checkcodes`
--

DROP TABLE IF EXISTS `checkcodes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `checkcodes` (
  `email` varchar(60) NOT NULL,
  `checkCode` varchar(45) DEFAULT NULL,
  `constructTime` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkcodes`
--

LOCK TABLES `checkcodes` WRITE;
/*!40000 ALTER TABLE `checkcodes` DISABLE KEYS */;
/*!40000 ALTER TABLE `checkcodes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dish00000001`
--

DROP TABLE IF EXISTS `dish00000001`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dish00000001` (
  `did` int(11) NOT NULL AUTO_INCREMENT,
  `dname` varchar(45) DEFAULT NULL,
  `dprice` double DEFAULT NULL,
  `damount` int(11) DEFAULT NULL,
  `dtype` varchar(45) DEFAULT NULL,
  `dbegin` int(11) DEFAULT NULL,
  `dend` int(11) DEFAULT NULL,
  PRIMARY KEY (`did`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish00000001`
--

LOCK TABLES `dish00000001` WRITE;
/*!40000 ALTER TABLE `dish00000001` DISABLE KEYS */;
INSERT INTO `dish00000001` VALUES (1,'口水鸡',29,30,'凉菜',20190319,20190420),(2,'西湖糯藕',22,30,'凉菜',20190319,20190420),(3,'糖醋里脊',29,50,'热菜',20190319,20190420),(4,'老底子三鲜',29,50,'热菜',20190319,20190420),(5,'蛋黄锅巴',19,50,'热菜',20190319,20190420),(6,'牛仔骨手工年糕',32,50,'热菜',20190319,20190420),(7,'番茄炒蛋',19,50,'热菜',20190319,20190420),(8,'可乐',5,200,'饮品',20190319,20190420),(9,'雪碧',5,200,'饮品',20190319,20190420),(10,'米饭',2,1000,'主食',20190319,20190420);
/*!40000 ALTER TABLE `dish00000001` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dish00000002`
--

DROP TABLE IF EXISTS `dish00000002`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dish00000002` (
  `did` int(11) NOT NULL AUTO_INCREMENT,
  `dname` varchar(45) DEFAULT NULL,
  `dprice` double DEFAULT NULL,
  `damount` int(11) DEFAULT NULL,
  `dtype` varchar(45) DEFAULT NULL,
  `dbegin` int(11) DEFAULT NULL,
  `dend` int(11) DEFAULT NULL,
  PRIMARY KEY (`did`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish00000002`
--

LOCK TABLES `dish00000002` WRITE;
/*!40000 ALTER TABLE `dish00000002` DISABLE KEYS */;
INSERT INTO `dish00000002` VALUES (1,'素什锦灶面',18,50,'素面',20190319,20190420),(2,'青菜素鸡灶面',18,50,'素面',20190319,20190420),(3,'青椒肉丝灶面',22,50,'荤面',20190319,20190420),(4,'红烧牛肉灶面',25,50,'荤面',20190319,20190420),(5,'米饭',1,1000,'主食',20190319,20190420);
/*!40000 ALTER TABLE `dish00000002` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dish00000003`
--

DROP TABLE IF EXISTS `dish00000003`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dish00000003` (
  `did` int(11) NOT NULL AUTO_INCREMENT,
  `dname` varchar(45) DEFAULT NULL,
  `dprice` double DEFAULT NULL,
  `damount` int(11) DEFAULT NULL,
  `dtype` varchar(45) DEFAULT NULL,
  `dbegin` int(11) DEFAULT NULL,
  `dend` int(11) DEFAULT NULL,
  PRIMARY KEY (`did`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish00000003`
--

LOCK TABLES `dish00000003` WRITE;
/*!40000 ALTER TABLE `dish00000003` DISABLE KEYS */;
INSERT INTO `dish00000003` VALUES (1,'经典麦辣鸡腿汉堡',20,100,'主食',20190319,20190420),(2,'巨无霸',23,100,'主食',20190319,20190420),(3,'川辣香鸡汉堡',21,100,'主食',20190319,20190420),(4,'椒盐金脆鸡翅一对',12.5,200,'小食',20190319,20190420),(5,'麦辣鸡翅',11.5,200,'小食',20190319,20190420),(6,'玉米杯',13,200,'小食',20190319,20190420),(7,'可口可乐',9.5,500,'饮品',20190319,20190420),(8,'雪碧',9.5,500,'饮品',20190319,20190420),(9,'纯牛奶',9,500,'饮品',20190319,20190420);
/*!40000 ALTER TABLE `dish00000003` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dish00000004`
--

DROP TABLE IF EXISTS `dish00000004`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dish00000004` (
  `did` int(11) NOT NULL AUTO_INCREMENT,
  `dname` varchar(45) DEFAULT NULL,
  `dprice` double DEFAULT NULL,
  `damount` int(11) DEFAULT NULL,
  `dtype` varchar(45) DEFAULT NULL,
  `dbegin` int(11) DEFAULT NULL,
  `dend` int(11) DEFAULT NULL,
  PRIMARY KEY (`did`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish00000004`
--

LOCK TABLES `dish00000004` WRITE;
/*!40000 ALTER TABLE `dish00000004` DISABLE KEYS */;
INSERT INTO `dish00000004` VALUES (1,'意大利面',24,200,'主食',20190319,20190420),(2,'9寸至尊披萨',34,200,'主食',20190319,20190420),(3,'9寸榴莲披萨',39.9,200,'主食',20190319,20190420),(4,'可乐',5,200,'饮品',20190319,20190420),(5,'雪碧',5,200,'饮品',20190319,20190420);
/*!40000 ALTER TABLE `dish00000004` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dish00000005`
--

DROP TABLE IF EXISTS `dish00000005`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dish00000005` (
  `did` int(11) NOT NULL AUTO_INCREMENT,
  `dname` varchar(45) DEFAULT NULL,
  `dprice` double DEFAULT NULL,
  `damount` int(11) DEFAULT NULL,
  `dtype` varchar(45) DEFAULT NULL,
  `dbegin` int(11) DEFAULT NULL,
  `dend` int(11) DEFAULT NULL,
  PRIMARY KEY (`did`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish00000005`
--

LOCK TABLES `dish00000005` WRITE;
/*!40000 ALTER TABLE `dish00000005` DISABLE KEYS */;
INSERT INTO `dish00000005` VALUES (1,'咖喱鸡肉焗饭',30.88,200,'焗饭',20190319,20190420),(2,'黑椒牛肉焗饭',18.8,200,'焗饭',20190319,20190420),(3,'黑椒牛肉焗面',32.88,200,'焗面',20190319,20190420),(4,'港式菠萝焗面',37.88,200,'焗面',20190319,20190420),(5,'可乐',3,200,'饮品',20190319,20190420),(6,'雪碧',3,200,'饮品',20190319,20190420);
/*!40000 ALTER TABLE `dish00000005` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dish00000006`
--

DROP TABLE IF EXISTS `dish00000006`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dish00000006` (
  `did` int(11) NOT NULL AUTO_INCREMENT,
  `dname` varchar(45) DEFAULT NULL,
  `dprice` double DEFAULT NULL,
  `damount` int(11) DEFAULT NULL,
  `dtype` varchar(45) DEFAULT NULL,
  `dbegin` int(11) DEFAULT NULL,
  `dend` int(11) DEFAULT NULL,
  PRIMARY KEY (`did`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish00000006`
--

LOCK TABLES `dish00000006` WRITE;
/*!40000 ALTER TABLE `dish00000006` DISABLE KEYS */;
INSERT INTO `dish00000006` VALUES (1,'招牌虾黄豆腐',28,100,'秦淮小厨',20190319,20190420),(2,'牛肉松',32,100,'秦淮小厨',20190319,20190420),(3,'绿豆糕',24,100,'秦淮小铺',20190319,20190420),(4,'榛子酥',18,100,'秦淮小铺',20190319,20190420),(5,'桂花米糕',22,100,'秦淮小铺',20190319,20190420),(6,'杨梅酒',48,100,'秦淮佳酿',20190319,20190420),(7,'桂花酒',58,100,'秦淮佳酿',20190319,20190420),(8,'红枣酒',48,100,'秦淮佳酿',20190319,20190420);
/*!40000 ALTER TABLE `dish00000006` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dish00000007`
--

DROP TABLE IF EXISTS `dish00000007`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dish00000007` (
  `did` int(11) NOT NULL AUTO_INCREMENT,
  `dname` varchar(45) DEFAULT NULL,
  `dprice` double DEFAULT NULL,
  `damount` int(11) DEFAULT NULL,
  `dtype` varchar(45) DEFAULT NULL,
  `dbegin` int(11) DEFAULT NULL,
  `dend` int(11) DEFAULT NULL,
  PRIMARY KEY (`did`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish00000007`
--

LOCK TABLES `dish00000007` WRITE;
/*!40000 ALTER TABLE `dish00000007` DISABLE KEYS */;
INSERT INTO `dish00000007` VALUES (1,'汉堡',20,100,'主食',20190322,20190330),(2,'意大利面',25,100,'主食',20190322,20190330),(3,'奶茶',7,100,'饮品',20190322,20190330);
/*!40000 ALTER TABLE `dish00000007` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group00000001`
--

DROP TABLE IF EXISTS `group00000001`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group00000001` (
  `gid` int(11) NOT NULL AUTO_INCREMENT,
  `gname` varchar(45) DEFAULT NULL,
  `gamount` int(11) DEFAULT NULL,
  `gprice` double DEFAULT NULL,
  `gdishes` varchar(45) DEFAULT NULL,
  `gbegin` int(11) DEFAULT NULL,
  `gend` int(11) DEFAULT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group00000001`
--

LOCK TABLES `group00000001` WRITE;
/*!40000 ALTER TABLE `group00000001` DISABLE KEYS */;
INSERT INTO `group00000001` VALUES (1,'番茄炒蛋套餐',30,22,'7,8,10',20190319,20190420);
/*!40000 ALTER TABLE `group00000001` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group00000002`
--

DROP TABLE IF EXISTS `group00000002`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group00000002` (
  `gid` int(11) NOT NULL AUTO_INCREMENT,
  `gname` varchar(45) DEFAULT NULL,
  `gamount` int(11) DEFAULT NULL,
  `gprice` double DEFAULT NULL,
  `gdishes` varchar(45) DEFAULT NULL,
  `gbegin` int(11) DEFAULT NULL,
  `gend` int(11) DEFAULT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group00000002`
--

LOCK TABLES `group00000002` WRITE;
/*!40000 ALTER TABLE `group00000002` DISABLE KEYS */;
INSERT INTO `group00000002` VALUES (1,'实惠双人餐',20,35,'1,3',20190319,20190420);
/*!40000 ALTER TABLE `group00000002` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group00000003`
--

DROP TABLE IF EXISTS `group00000003`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group00000003` (
  `gid` int(11) NOT NULL AUTO_INCREMENT,
  `gname` varchar(45) DEFAULT NULL,
  `gamount` int(11) DEFAULT NULL,
  `gprice` double DEFAULT NULL,
  `gdishes` varchar(45) DEFAULT NULL,
  `gbegin` int(11) DEFAULT NULL,
  `gend` int(11) DEFAULT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group00000003`
--

LOCK TABLES `group00000003` WRITE;
/*!40000 ALTER TABLE `group00000003` DISABLE KEYS */;
INSERT INTO `group00000003` VALUES (1,'川辣香鸡堡大套餐',100,37.5,'3,5,7',20190319,20190420),(2,'麦辣鸡腿汉堡套餐',100,36,'1,5,8',20190319,20190420);
/*!40000 ALTER TABLE `group00000003` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group00000004`
--

DROP TABLE IF EXISTS `group00000004`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group00000004` (
  `gid` int(11) NOT NULL AUTO_INCREMENT,
  `gname` varchar(45) DEFAULT NULL,
  `gamount` int(11) DEFAULT NULL,
  `gprice` double DEFAULT NULL,
  `gdishes` varchar(45) DEFAULT NULL,
  `gbegin` int(11) DEFAULT NULL,
  `gend` int(11) DEFAULT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group00000004`
--

LOCK TABLES `group00000004` WRITE;
/*!40000 ALTER TABLE `group00000004` DISABLE KEYS */;
INSERT INTO `group00000004` VALUES (1,'实惠双人餐',150,60,'1,2,4,5',20190319,20190420);
/*!40000 ALTER TABLE `group00000004` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group00000005`
--

DROP TABLE IF EXISTS `group00000005`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group00000005` (
  `gid` int(11) NOT NULL AUTO_INCREMENT,
  `gname` varchar(45) DEFAULT NULL,
  `gamount` int(11) DEFAULT NULL,
  `gprice` double DEFAULT NULL,
  `gdishes` varchar(45) DEFAULT NULL,
  `gbegin` int(11) DEFAULT NULL,
  `gend` int(11) DEFAULT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group00000005`
--

LOCK TABLES `group00000005` WRITE;
/*!40000 ALTER TABLE `group00000005` DISABLE KEYS */;
INSERT INTO `group00000005` VALUES (1,'实惠三人餐',100,90,'1,3,4,5,5,5',20190319,20190420);
/*!40000 ALTER TABLE `group00000005` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group00000006`
--

DROP TABLE IF EXISTS `group00000006`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group00000006` (
  `gid` int(11) NOT NULL AUTO_INCREMENT,
  `gname` varchar(45) DEFAULT NULL,
  `gamount` int(11) DEFAULT NULL,
  `gprice` double DEFAULT NULL,
  `gdishes` varchar(45) DEFAULT NULL,
  `gbegin` int(11) DEFAULT NULL,
  `gend` int(11) DEFAULT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group00000006`
--

LOCK TABLES `group00000006` WRITE;
/*!40000 ALTER TABLE `group00000006` DISABLE KEYS */;
/*!40000 ALTER TABLE `group00000006` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group00000007`
--

DROP TABLE IF EXISTS `group00000007`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group00000007` (
  `gid` int(11) NOT NULL AUTO_INCREMENT,
  `gname` varchar(45) DEFAULT NULL,
  `gamount` int(11) DEFAULT NULL,
  `gprice` double DEFAULT NULL,
  `gdishes` varchar(45) DEFAULT NULL,
  `gbegin` int(11) DEFAULT NULL,
  `gend` int(11) DEFAULT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group00000007`
--

LOCK TABLES `group00000007` WRITE;
/*!40000 ALTER TABLE `group00000007` DISABLE KEYS */;
INSERT INTO `group00000007` VALUES (1,'套餐1',200,25,'1,3',20190322,20190330);
/*!40000 ALTER TABLE `group00000007` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `oid` int(11) NOT NULL AUTO_INCREMENT,
  `customerID` varchar(45) DEFAULT NULL,
  `canteenID` int(11) DEFAULT NULL,
  `timeBegin` varchar(45) DEFAULT NULL,
  `state` varchar(45) DEFAULT NULL,
  `destination` varchar(45) DEFAULT NULL,
  `dishSum` double DEFAULT NULL,
  `deliverySum` double DEFAULT NULL,
  `dishes` varchar(45) DEFAULT NULL,
  `groups` varchar(45) DEFAULT NULL,
  `deliveryID` varchar(45) DEFAULT NULL,
  `cancel` varchar(45) DEFAULT NULL,
  `discountSum` double DEFAULT NULL,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'yunli@163.com',1,'2019/03/19 18:00:42','Arrived','鼓楼区龙全花园 北京西路8号02幢',60,2.9699999999999998,'10,10,2,3,8','','156894','Cancel',0),(2,'yunli@163.com',5,'2019/03/19 18:01:05','Arrived','鼓楼区龙全花园 北京西路8号02幢',122.88,1.5,'3','1','156894','Cancel',0),(3,'yunli@163.com',3,'2019/03/19 18:01:25','Cancel','鼓楼区龙全花园 北京西路8号02幢',59.5,4.949999999999999,'1,5,7,7,9','','156894','WaitingDelivery',0),(4,'yunli@163.com',4,'2019/03/19 18:02:26','Arrived','鼓楼区龙全花园 北京西路8号02幢',39,1.47,'2,4','','156894','Cancel',0),(5,'shichu@163.com',4,'2019/03/19 18:04:19','Cancel','鼓楼区宁乐新苑 大方巷18号',34,1.98,'1,4,4','','156894','WaitingAccept',0),(6,'shichu@163.com',2,'2019/03/19 18:04:38','Arrived','鼓楼区宁乐新苑 大方巷18号',105,4.89,'1,2,3,3,4','','156894','Cancel',0),(7,'shichu@163.com',1,'2019/03/19 18:04:57','Arrived','鼓楼区宁乐新苑 大方巷18号',103,4.53,'10,10,2,3,4,7','','156894','Cancel',0),(8,'shichu@163.com',6,'2019/03/19 18:05:16','Arrived','鼓楼区宁乐新苑 大方巷18号',136,3.06,'6,2,2,3','','156894','Cancel',0),(9,'shichu@163.com',3,'2019/03/19 18:05:33','Arrived','鼓楼区宁乐新苑 大方巷18号',89,6.720000000000001,'1,1,6','2','156894','Cancel',0),(10,'yunli@163.com',2,'2019/03/19 18:15:55','Arrived','鼓楼区龙全花园 北京西路8号02幢',35,2.19,'','1','156894','Cancel',2),(11,'shichu@163.com',1,'2019/03/19 18:20:41','Arrived','鼓楼区宁乐新苑 大方巷18号',86,4.53,'1,5,7,7','','156894','Cancel',8),(12,'yunli@163.com',2,'2019/03/22 09:05:20','Arrived','鼓楼区龙全花园 北京西路8号02幢',37,2.19,'5,1,1','','156894','Cancel',2),(13,'yunli@163.com',2,'2019/03/22 09:06:03','WaitingPay','鼓楼区龙全花园 北京西路8号02幢',22,2.178,'3','','156894','Cancel',0),(14,'shichu@163.com',7,'2019/03/22 10:09:03','WaitingPay','鼓楼区宁乐新苑 大方巷18号',52,3.93,'1,3','1','156894','Cancel',4),(15,'shichu@163.com',1,'2019/03/22 10:09:30','Cancel','鼓楼区宁乐新苑 大方巷18号',61,3.96,'1,6','','156894','WaitingPay',0),(16,'shichu@163.com',2,'2019/03/22 10:09:50','Arrived','鼓楼区宁乐新苑 大方巷18号',19,4.89,'5,1','','156894','Cancel',0);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pay_record`
--

DROP TABLE IF EXISTS `pay_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pay_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `payTime` varchar(45) DEFAULT NULL,
  `ordersDate` varchar(45) DEFAULT NULL,
  `dishSum` double DEFAULT NULL,
  `discountSum` double DEFAULT NULL,
  `deliverySum` double DEFAULT NULL,
  `payAmount` double DEFAULT NULL,
  `valid` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_record`
--

LOCK TABLES `pay_record` WRITE;
/*!40000 ALTER TABLE `pay_record` DISABLE KEYS */;
INSERT INTO `pay_record` VALUES (1,'2019/03/22 10:12:44','2019/03/19 00:00:00',869.38,10,38.79,889.1883,'1'),(2,'2019/03/22 09:09:55','2019/03/20 00:00:00',0,0,0,0,'1'),(3,'2019/03/22 09:10:00','2019/03/21 00:00:00',0,0,0,0,'1');
/*!40000 ALTER TABLE `pay_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `updatecanteen`
--

DROP TABLE IF EXISTS `updatecanteen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `updatecanteen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) DEFAULT NULL,
  `hostName` varchar(45) DEFAULT NULL,
  `idNumber` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `account` varchar(45) DEFAULT NULL,
  `cname` varchar(45) DEFAULT NULL,
  `province` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `area` varchar(45) DEFAULT NULL,
  `caddress` varchar(45) DEFAULT NULL,
  `ctype` varchar(45) DEFAULT NULL,
  `cbegin` varchar(45) DEFAULT NULL,
  `cend` varchar(45) DEFAULT NULL,
  `applyDate` varchar(45) DEFAULT NULL,
  `check` varchar(45) DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `lng` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `updatecanteen`
--

LOCK TABLES `updatecanteen` WRITE;
/*!40000 ALTER TABLE `updatecanteen` DISABLE KEYS */;
INSERT INTO `updatecanteen` VALUES (1,2,'世兰','640102020202020202','wx8339459@163.com','shilan','shilan','翊坤宫','江苏','南京','鼓楼区','南京大学(鼓楼校区) 江苏省南京市鼓楼区汉口路22号','汉餐','09:00:00','21:00:00','2019/03/22 09:09:07','pass',32.06143,118.78601);
/*!40000 ALTER TABLE `updatecanteen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `uname` varchar(45) DEFAULT NULL,
  `usex` enum('男','女') DEFAULT NULL,
  `uemail` varchar(45) DEFAULT NULL,
  `upassword` varchar(45) DEFAULT NULL,
  `uphone` varchar(45) DEFAULT NULL,
  `ucredits` varchar(45) DEFAULT NULL,
  `upay` varchar(45) DEFAULT NULL,
  `valid` varchar(45) DEFAULT NULL,
  `applyDate` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'允礼','男','yunli@163.com','yunli','13801010101','27','yunli','0','2019/03/19 17:07:40'),(2,'实初','男','shichu@163.com','shichu','13802020202','50','shichu','1','2019/03/19 17:11:21'),(3,'王旭','男','wangxu@163.com','wangxu','15952023319','0','yunli','1','2019/03/22 10:01:52');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vip`
--

DROP TABLE IF EXISTS `vip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vip` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `beginTime` varchar(45) DEFAULT NULL,
  `endTime` varchar(45) DEFAULT NULL,
  `valid` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vip`
--

LOCK TABLES `vip` WRITE;
/*!40000 ALTER TABLE `vip` DISABLE KEYS */;
INSERT INTO `vip` VALUES (1,1,'2019/03/19 18:14:49','2019/04/19 18:14:49','1'),(2,2,'2019/03/19 18:20:13','2019/04/19 18:20:13','1');
/*!40000 ALTER TABLE `vip` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-27 14:46:59
