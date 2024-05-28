
SET NAMES utf8mb4;

CREATE database if NOT EXISTS `marketing-platform` default character set utf8mb4 collate utf8mb4_0900_ai_ci;
use `marketing-platform`;

# 转储表 prize
# ------------------------------------------------------------

DROP TABLE IF EXISTS `prize`;

CREATE TABLE `prize` (
                         `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                         `code` varchar(8) NOT NULL COMMENT '抽奖奖品编码 - 内部流转使用',
                         `key` varchar(32) NOT NULL COMMENT '奖品对接标识 - 每一个都是一个对应的发奖策略',
                         `config` varchar(32) NOT NULL COMMENT '奖品配置信息',
                         `description` varchar(128) NOT NULL COMMENT '奖品内容描述',
                         `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `prize` WRITE;

INSERT INTO `prize` (`id`, `code`, `key`, `config`, `description`)
VALUES
    (1,'100','user_credit_blacklist','1','黑名单积分'),
    (2,'101','user_credit_random','1,100','用户积分【优先透彻规则范围，如果没有则走配置】'),
    (3,'102','openai_use_count','5','OpenAI 增加使用次数'),
    (4,'103','openai_use_count','10','OpenAI 增加使用次数'),
    (5,'104','openai_use_count','20','OpenAI 增加使用次数'),
    (6,'105','openai_model','gpt-4','OpenAI 增加模型'),
    (7,'106','openai_model','dall-e-2','OpenAI 增加模型'),
    (8,'107','openai_model','dall-e-3','OpenAI 增加模型'),
    (9,'108','openai_use_count','100','OpenAI 增加使用次数'),
    (10,'109','openai_model','gpt-4,dall-e-2,dall-e-3','OpenAI 增加模型');

UNLOCK TABLES;


# 转储表 raffle_strategy
# ------------------------------------------------------------

DROP TABLE IF EXISTS `raffle_strategy`;

CREATE TABLE `raffle_strategy` (
                            `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                            `code` varchar(8) NOT NULL COMMENT '抽奖策略编码',
                            `rule_models` varchar(256) DEFAULT NULL COMMENT '规则模型，rule配置的模型同步到此表，便于使用',
                            `description` varchar(128) NOT NULL COMMENT '抽奖策略描述',
                            `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`),
                            KEY `idx_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `raffle_strategy` WRITE;

INSERT INTO `raffle_strategy` (`id`, `code`, `rule_models`, `description`)
VALUES
    (1,'100001', 'weight,blacklist','抽奖策略'),
    (2,'100002', NULL,'抽奖策略');

UNLOCK TABLES;


# 转储表 raffle_strategy_prize
# ------------------------------------------------------------

DROP TABLE IF EXISTS `raffle_strategy_prize`;

CREATE TABLE `raffle_strategy_prize` (
                                  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                  `strategy_code` varchar(8) NOT NULL COMMENT '抽奖策略编码',
                                  `prize_code` varchar(8) NOT NULL COMMENT '奖品编码 - 内部流转使用',
                                  `title` varchar(128) NOT NULL COMMENT '奖品标题',
                                  `subtitle` varchar(128) DEFAULT NULL COMMENT '奖品副标题',
                                  `total_inventory` int(8) NOT NULL DEFAULT '0' COMMENT '奖品库存总量',
                                  `surplus_inventory` int(8) NOT NULL DEFAULT '0' COMMENT '奖品库存剩余量',
                                  `rate` decimal(6,4) NOT NULL COMMENT '奖品中奖概率',
                                  `rule_models` varchar(256) DEFAULT NULL COMMENT '规则模型，rule配置的模型同步到此表，便于使用',
                                  `sort` int(2) NOT NULL DEFAULT '0' COMMENT '排序',
                                  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                  PRIMARY KEY (`id`),
                                  KEY `idx_strategy_code_prize_code` (`strategy_code`,`prize_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `raffle_strategy_prize` WRITE;

INSERT INTO `raffle_strategy_prize` (`id`, `strategy_code`, `prize_code`, `title`, `subtitle`, `total_inventory`, `surplus_inventory`, `rate`, `rule_models`, `sort`)
VALUES
    (1,'100001','101','随机积分',NULL,80000,80000,80.0000,'random,lucky',1),
    (2,'100001','102','5次使用',NULL,10000,10000,10.0000,'lucky',2),
    (3,'100001','103','10次使用',NULL,5000,5000,5.0000,'lucky',3),
    (4,'100001','104','20次使用',NULL,4000,4000,4.0000,'lucky',4),
    (5,'100001','105','增加gpt-4对话模型',NULL,600,600,2.0000,'lucky',5),
    (6,'100001','106','增加dall-e-2画图模型',NULL,200,200,2.0000,'lucky',6),
    (7,'100001','107','增加dall-e-3画图模型','抽奖1次后解锁',200,200,1.0000,'lock,lucky',7),
    (8,'100001','108','100次使用','抽奖2次后解锁',199,199,1.0000,'lock,lucky',8),
    (9,'100001','109','解锁全部模型','抽奖6次后解锁',1,1,0.0500,'lock,lucky',9),
    (10,'100002','101','随机积分',NULL,1,1,0.5000,'random,lucky',1),
	(11,'100002','102','5次使用',NULL,1,1,0.1000,'random,lucky',2),
	(12,'100002','106','增加dall-e-2画图模型',NULL,1,1,0.0100,'random,lucky',3);

UNLOCK TABLES;


# 转储表 raffle_strategy_rule
# ------------------------------------------------------------

DROP TABLE IF EXISTS `raffle_strategy_rule`;

CREATE TABLE `raffle_strategy_rule` (
 `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
 `strategy_code` varchar(8) NOT NULL COMMENT '抽奖策略编码',
 `prize_code` varchar(8) DEFAULT NULL COMMENT '奖品编码【规则类型为策略，则不需要奖品ID】',
 `type` varchar(12) NOT NULL DEFAULT '0' COMMENT '抽象规则类型；STRATEGY-策略规则、PRIZE-奖品规则',
 `model` varchar(16) NOT NULL COMMENT '抽奖规则类型【random - 随机值计算、lock - 抽奖几次后解锁、lucky - 幸运奖(兜底奖品)、weight - 权重、blacklist - 黑名单】',
 `value` varchar(256) NOT NULL COMMENT '抽奖规则比值',
 `description` varchar(128) NOT NULL COMMENT '抽奖规则描述',
 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 PRIMARY KEY (`id`),
 KEY `idx_strategy_code_prize_code` (`strategy_code`,`prize_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `raffle_strategy_rule` WRITE;

INSERT INTO `raffle_strategy_rule` (`id`, `strategy_code`, `prize_code`, `type`, `model`, `value`, `description`)
VALUES
	(1,'100001','101','PRIZE','random','1,1000','随机积分规则'),
	(2,'100001','107','PRIZE','lock','1','抽奖1次后解锁'),
	(3,'100001','108','PRIZE','lock','2','抽奖2次后解锁'),
	(4,'100001','109','PRIZE','lock','6','抽奖6次后解锁'),
	(5,'100001','101','PRIZE','lucky','1,10','兜底奖品10以内随机积分'),
	(6,'100001','102','PRIZE','lucky','1,20','兜底奖品20以内随机积分'),
	(7,'100001','103','PRIZE','lucky','1,30','兜底奖品30以内随机积分'),
	(8,'100001','104','PRIZE','lucky','1,40','兜底奖品40以内随机积分'),
	(9,'100001','105','PRIZE','lucky','1,50','兜底奖品50以内随机积分'),
	(10,'100001','106','PRIZE','lucky','1,60','兜底奖品60以内随机积分'),
	(11,'100001','107','PRIZE','lucky','1,100','兜底奖品100以内随机积分'),
	(12,'100001','108','PRIZE','lucky','1,100','兜底奖品100以内随机积分'),
	(13,'100001',NULL,'STRATEGY','weight','4000:102,103,104,105,106 5000:105,106,107,108 6000:107,108,109','积分权重规则'),
	(14,'100001',NULL,'STRATEGY','blacklist','100:user001,user002,user003','黑名单，1积分兜底');

UNLOCK TABLES;