
/*
create database ssm;

use ssm;



*/

CREATE TABLE `user` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`username` varchar(32) NOT NULL unique COMMENT '用户名称',
`password` varchar(30) NOT NULL COMMENT 'pwd',
`name` varchar(32) NOT NULL unique COMMENT '用户名称',
`birthday` date DEFAULT NULL COMMENT '生日',
`sex` char(1) DEFAULT NULL COMMENT '性别',
`address` varchar(256) DEFAULT NULL COMMENT '地址',

constraint pk_user primary key (`id`)
);