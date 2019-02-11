/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50714
Source Host           : localhost:3306
Source Database       : miaosha

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2019-02-11 13:40:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `price` double(10,0) NOT NULL DEFAULT '0',
  `description` varchar(500) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `sales` int(11) NOT NULL DEFAULT '0',
  `img_url` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES ('1', '??', '3', '?????', '11', 'http://img0.imgtn.bdimg.com/it/u=221058000,2197243584&fm=26&gp=0.jpg');
INSERT INTO `item` VALUES ('2', '??', '5', '?????', '9', 'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3864937088,3230638921&fm=26&gp=0.jpg');
INSERT INTO `item` VALUES ('3', '??', '100000', '????', '0', 'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=74898417,3680650868&fm=26&gp=0.jpg');
INSERT INTO `item` VALUES ('4', '??', '10000', '?????????????????', '3', 'http://img3.imgtn.bdimg.com/it/u=4175852259,2263926100&fm=26&gp=0.jpg');
INSERT INTO `item` VALUES ('5', '???', '200', '?????', '1', 'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1146202406,771992033&fm=27&gp=0.jpg');

-- ----------------------------
-- Table structure for item_stock
-- ----------------------------
DROP TABLE IF EXISTS `item_stock`;
CREATE TABLE `item_stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stock` int(255) NOT NULL DEFAULT '0',
  `item_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of item_stock
-- ----------------------------
INSERT INTO `item_stock` VALUES ('1', '82', '1');
INSERT INTO `item_stock` VALUES ('2', '182', '2');
INSERT INTO `item_stock` VALUES ('3', '5', '3');
INSERT INTO `item_stock` VALUES ('4', '1', '4');
INSERT INTO `item_stock` VALUES ('5', '19', '5');

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `item_id` int(11) NOT NULL DEFAULT '0',
  `item_price` double(10,2) NOT NULL DEFAULT '0.00',
  `amount` int(11) NOT NULL DEFAULT '0',
  `order_price` double(10,2) NOT NULL DEFAULT '0.00',
  `promo_id` int(11) NOT NULL DEFAULT '0' COMMENT '非0就可以秒杀',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES ('', '1', '2', '0.00', '1', '5.00', '0');
INSERT INTO `order_info` VALUES ('2019020900001000', '1', '2', '0.00', '1', '5.00', '0');
INSERT INTO `order_info` VALUES ('2019020900001100', '1', '2', '0.00', '1', '5.00', '0');
INSERT INTO `order_info` VALUES ('2019020900001200', '1', '2', '0.00', '1', '5.00', '0');
INSERT INTO `order_info` VALUES ('2019020900001300', '1', '2', '0.00', '1', '5.00', '0');
INSERT INTO `order_info` VALUES ('2019020900001400', '1', '1', '3.00', '1', '3.00', '0');
INSERT INTO `order_info` VALUES ('2019020900001500', '1', '1', '3.00', '1', '3.00', '0');
INSERT INTO `order_info` VALUES ('2019020900001600', '1', '1', '3.00', '1', '3.00', '0');
INSERT INTO `order_info` VALUES ('2019020900001700', '1', '1', '3.00', '1', '3.00', '0');
INSERT INTO `order_info` VALUES ('2019020900001800', '1', '1', '3.00', '1', '3.00', '0');
INSERT INTO `order_info` VALUES ('2019020900001900', '1', '1', '3.00', '1', '3.00', '0');
INSERT INTO `order_info` VALUES ('2019021000002000', '1', '1', '3.00', '1', '3.00', '0');
INSERT INTO `order_info` VALUES ('2019021000002100', '1', '1', '3.00', '1', '3.00', '0');
INSERT INTO `order_info` VALUES ('2019021000002200', '1', '1', '3.00', '1', '3.00', '0');
INSERT INTO `order_info` VALUES ('2019021000002300', '1', '1', '3.00', '1', '3.00', '0');
INSERT INTO `order_info` VALUES ('2019021000002400', '1', '1', '3.00', '1', '3.00', '0');
INSERT INTO `order_info` VALUES ('2019021000002500', '1', '1', '3.00', '1', '3.00', '0');
INSERT INTO `order_info` VALUES ('2019021000002600', '1', '1', '3.00', '1', '3.00', '0');
INSERT INTO `order_info` VALUES ('2019021000002700', '1', '2', '5.00', '1', '5.00', '0');
INSERT INTO `order_info` VALUES ('2019021000002800', '1', '2', '5.00', '1', '5.00', '0');
INSERT INTO `order_info` VALUES ('2019021000002900', '1', '2', '5.00', '1', '5.00', '0');
INSERT INTO `order_info` VALUES ('2019021000003000', '1', '2', '5.00', '1', '5.00', '0');
INSERT INTO `order_info` VALUES ('2019021100003100', '4', '2', '1.50', '1', '5.00', '2');
INSERT INTO `order_info` VALUES ('2019021100003200', '4', '2', '1.50', '1', '5.00', '2');
INSERT INTO `order_info` VALUES ('2019021100003300', '4', '1', '3.00', '1', '3.00', '0');
INSERT INTO `order_info` VALUES ('2019021100003400', '4', '1', '3.00', '1', '3.00', '0');
INSERT INTO `order_info` VALUES ('2019021100003500', '4', '1', '3.00', '1', '3.00', '0');
INSERT INTO `order_info` VALUES ('2019021100003600', '5', '5', '200.00', '1', '200.00', '0');

-- ----------------------------
-- Table structure for promo
-- ----------------------------
DROP TABLE IF EXISTS `promo`;
CREATE TABLE `promo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `promo_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `item_id` int(11) NOT NULL DEFAULT '0',
  `promo_item_price` double(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of promo
-- ----------------------------
INSERT INTO `promo` VALUES ('2', 'orange', '2019-02-10 11:47:33', '2019-02-12 11:47:35', '2', '1.50');
INSERT INTO `promo` VALUES ('3', 'elephant', '2019-09-21 13:21:44', '2019-12-19 12:58:59', '3', '15000.00');

-- ----------------------------
-- Table structure for sequence_info
-- ----------------------------
DROP TABLE IF EXISTS `sequence_info`;
CREATE TABLE `sequence_info` (
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `current_value` int(11) NOT NULL DEFAULT '0',
  `step` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of sequence_info
-- ----------------------------
INSERT INTO `sequence_info` VALUES ('order_info', '37', '1');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `gender` tinyint(255) NOT NULL DEFAULT '0' COMMENT '1男 2女',
  `age` int(11) NOT NULL DEFAULT '0',
  `telphone` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `register_mode` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '//byalipay,byphone,bywechat',
  `third_party_id` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `telphone` (`telphone`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', '1', '1', '1', '1', 'byphone', '');
INSERT INTO `user_info` VALUES ('2', '3', '1', '3', '3', 'byphone', '');
INSERT INTO `user_info` VALUES ('3', '4', '1', '4', '4', 'byphone', '');
INSERT INTO `user_info` VALUES ('4', '5', '2', '5', '5', 'byphone', '');
INSERT INTO `user_info` VALUES ('5', '13900000000', '1', '22', '13900000000', 'byphone', '');

-- ----------------------------
-- Table structure for user_password
-- ----------------------------
DROP TABLE IF EXISTS `user_password`;
CREATE TABLE `user_password` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `encrpt_password` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `user_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of user_password
-- ----------------------------
INSERT INTO `user_password` VALUES ('1', 'xMpCOKC5I4INzFCab3WEmw==', '1');
INSERT INTO `user_password` VALUES ('2', '7MvIfktc4v4oMI/Z8qe68w==', '2');
INSERT INTO `user_password` VALUES ('3', 'qH/2eaLz5x2RgaZ7dUISLA==', '3');
INSERT INTO `user_password` VALUES ('4', '5No7f7vOI0XXdysGdKMY1Q==', '4');
INSERT INTO `user_password` VALUES ('5', 'Ru7D8z49hqQMkUpZGSL0IA==', '5');
