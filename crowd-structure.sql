/*
 Navicat Premium Data Transfer

 Source Server         : 服务外包
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Host           : 47.97.33.107:3306
 Source Schema         : travel-zxc

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001

 Date: 14/04/2023 16:11:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for crowd
-- ----------------------------
DROP TABLE IF EXISTS `crowd`;
CREATE TABLE `crowd`  (
  `time` date NOT NULL COMMENT '检测时间',
  `total_track` int(11) NOT NULL COMMENT '时间范围内的总人流量',
  `current_track` int(11) NOT NULL COMMENT '当前客流量',
  `up_nums` int(11) NOT NULL COMMENT '向上客流量',
  `down_nums` int(11) NOT NULL COMMENT '向下客流量',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '定位（经纬度）',
  `scattered_nums` int(11) NOT NULL COMMENT '散团数',
  `boutique_nums` int(11) NOT NULL COMMENT '精品团数',
  PRIMARY KEY (`time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
