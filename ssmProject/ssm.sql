
create database if not exists ssm character set utf8;

use `ssm`;

drop TABLE if EXISTS `user`;

CREATE TABLE `user` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`username` varchar(32) NOT NULL unique COMMENT '用户名称',
`password` varchar(30) NOT NULL COMMENT 'pwd',
`name` varchar(32) NOT NULL unique COMMENT '用户名称',
`birthday` date DEFAULT NULL COMMENT '生日',
`sex` char(1) DEFAULT NULL COMMENT '性别',
`address` varchar(256) DEFAULT NULL COMMENT '地址',
`create_time` datetime COMMENT '添加时间',

 primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET utf8 COLLATE utf8_general_ci;


CREATE TABLE `temp_tab` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`code` varchar(20) DEFAULT NULL COMMENT '编码',
`name` varchar(20) NOT NULL unique COMMENT '名称',
`update_date` datetime comment '最后修改时间',
`is_del` char(1) default '0' comment '1=删除',

constraint pk_user primary key (`id`)
);

insert  user(username,password,name,birthday,sex,address) values('aa','aa','aa',now(),'a','aa');

insert into temp_tab(code,name,update_date) values('ccc','nnn',now());
insert into temp_tab(code,name,update_date) values('cc','nn',DATE_FORMAT('2018-02-12 13:23:02','%Y-%m-%d %T'));

select * from user;
select * from temp_tab;