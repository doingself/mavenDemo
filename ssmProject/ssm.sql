
/*
-- create database ssm;
*/

use `ssm`;

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


insert  user(username,password,name,birthday,sex,address) values('aa','aa','aa',null,'a','aa');


CREATE TABLE `temp_tab` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`code` varchar(20) DEFAULT NULL COMMENT '编码',
`name` varchar(20) NOT NULL unique COMMENT '名称',

constraint pk_user primary key (`id`)
);

insert into temp_tab(code,name) values('ccc','nnn');

select * from user;
select * from temp_tab;