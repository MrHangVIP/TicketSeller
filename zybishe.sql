-- MySQL dump 10.13  Distrib 5.5.39, for Win64 (x86)
--
-- Host: localhost    Database: zybishe
-- ------------------------------------------------------
-- Server version	5.5.39

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
-- Table structure for table `t_account`
--
use  zybishe;
DROP TABLE IF EXISTS `t_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_account` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userid` int(10) unsigned NOT NULL,
  `money` int(10) unsigned NOT NULL,
  `createtime` varchar(255) NOT NULL,
  `updatetime` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_account`
--

LOCK TABLES `t_account` WRITE;
/*!40000 ALTER TABLE `t_account` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_account_record`
--

DROP TABLE IF EXISTS `t_account_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_account_record` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `accountid` int(10) unsigned NOT NULL,
  `action` varchar(255) NOT NULL,
  `creattime` varchar(255) NOT NULL,
  `change` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_account_record`
--

LOCK TABLES `t_account_record` WRITE;
/*!40000 ALTER TABLE `t_account_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_account_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_business`
--

DROP TABLE IF EXISTS `t_business`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_business` (
  `businessid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userphone` varchar(255) NOT NULL COMMENT '手机号',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `nickname` varchar(255) NOT NULL COMMENT '昵称',
  `createtime` varchar(255) NOT NULL COMMENT '创建时间',
  `updatetime` varchar(255) NOT NULL COMMENT '修改时间',
  `headurl` varchar(255) NOT NULL COMMENT '头像地址',
  PRIMARY KEY (`businessid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_business`
--

LOCK TABLES `t_business` WRITE;
/*!40000 ALTER TABLE `t_business` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_business` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_buy_record`
--

DROP TABLE IF EXISTS `t_buy_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_buy_record` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(10) unsigned NOT NULL COMMENT '用户id',
  `ticketid` int(10) unsigned NOT NULL COMMENT '票务id',
  `businessid` int(10) unsigned NOT NULL COMMENT '商家id',
  `createtime` varchar(255) NOT NULL COMMENT '创建时间',
  `updatetime` varchar(255) NOT NULL COMMENT '更新时间',
  `ticket_time` varchar(255) NOT NULL COMMENT '场次',
  `state` varchar(255) NOT NULL COMMENT '状态',
  `price` double NOT NULL COMMENT '单价',
  `num` int(10) unsigned NOT NULL COMMENT '数量',
  `money` double NOT NULL COMMENT '总价',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_buy_record`
--

LOCK TABLES `t_buy_record` WRITE;
/*!40000 ALTER TABLE `t_buy_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_buy_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_comment`
--

DROP TABLE IF EXISTS `t_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_comment` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(10) unsigned NOT NULL COMMENT '用户id',
  `ticketid` int(10) unsigned NOT NULL COMMENT '票务id',
  `content` varchar(255) NOT NULL COMMENT '评价内容',
  `createtime` varchar(255) NOT NULL COMMENT '创建时间',
  `starnum` int(10) unsigned NOT NULL COMMENT '评分数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_comment`
--

LOCK TABLES `t_comment` WRITE;
/*!40000 ALTER TABLE `t_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_favor`
--

DROP TABLE IF EXISTS `t_favor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_favor` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userid` int(10) unsigned NOT NULL COMMENT '用户id',
  `ticketid` int(10) unsigned NOT NULL COMMENT '票务id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_favor`
--

LOCK TABLES `t_favor` WRITE;
/*!40000 ALTER TABLE `t_favor` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_favor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_price`
--

DROP TABLE IF EXISTS `t_price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_price` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `price` double NOT NULL COMMENT '价格',
  `ticketid` int(10) unsigned NOT NULL COMMENT '票务id',
  `message` varchar(255) NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_price`
--

LOCK TABLES `t_price` WRITE;
/*!40000 ALTER TABLE `t_price` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_price` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_ticket`
--

DROP TABLE IF EXISTS `t_ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_ticket` (
  `ticketid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `businessid` int(10) unsigned NOT NULL COMMENT '商家id',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `typeid` int(10) unsigned NOT NULL COMMENT '类型id',
  `biref` varchar(255) NOT NULL COMMENT '简介',
  `indexpicurl` varchar(255) NOT NULL COMMENT '索引图url',
  `createtime` varchar(255) NOT NULL COMMENT '创建时间',
  `address` varchar(255) NOT NULL COMMENT '地址',
  `finishtime` varchar(255) NOT NULL COMMENT '结束时间',
  `finishtimestmp` longtext NOT NULL COMMENT '结束时间戳',
  `starttime` varchar(255) NOT NULL COMMENT '开始时间',
  `starttimestmp` longtext NOT NULL COMMENT '开始时间戳',
  PRIMARY KEY (`ticketid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_ticket`
--

LOCK TABLES `t_ticket` WRITE;
/*!40000 ALTER TABLE `t_ticket` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_ticket_num`
--

DROP TABLE IF EXISTS `t_ticket_num`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_ticket_num` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ticketid` int(10) unsigned NOT NULL,
  `last` int(10) unsigned NOT NULL,
  `max` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_ticket_num`
--

LOCK TABLES `t_ticket_num` WRITE;
/*!40000 ALTER TABLE `t_ticket_num` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_ticket_num` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_time`
--

DROP TABLE IF EXISTS `t_time`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_time` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ticketid` int(10) unsigned NOT NULL,
  `message` varchar(255) NOT NULL,
  `finishtime` varchar(255) NOT NULL,
  `finishtimestmp` longtext NOT NULL,
  `starttime` varchar(255) NOT NULL,
  `starttimestmp` longtext NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_time`
--

LOCK TABLES `t_time` WRITE;
/*!40000 ALTER TABLE `t_time` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_time` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_type`
--

DROP TABLE IF EXISTS `t_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_type` (
  `typeid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `value` varchar(255) NOT NULL COMMENT '类型名',
  `order` int(10) unsigned NOT NULL COMMENT '顺序',
  PRIMARY KEY (`typeid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_type`
--

LOCK TABLES `t_type` WRITE;
/*!40000 ALTER TABLE `t_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user` (
  `userId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userphone` varchar(255) NOT NULL COMMENT '用户名',
  `userpass` varchar(255) NOT NULL COMMENT '密码',
  `nickname` varchar(255) NOT NULL COMMENT '昵称',
  `createtime` varchar(255) NOT NULL COMMENT '创建时间',
  `city` varchar(255) NOT NULL COMMENT '城市',
  `birthday` varchar(255) NOT NULL COMMENT '生日',
  `sex` varchar(255) NOT NULL COMMENT '性别',
  `headurl` varchar(255) NOT NULL COMMENT '头像地址',
  `lastupdatetime` varchar(255) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-07 22:46:55