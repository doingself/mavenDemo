
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


create table game(
`id` int(11) AUTO_INCREMENT,
`name` varchar(20) COMMENT '赛事名称'
`remark` varchar(20) COMMENT '备注',
`status` int COMMENT '状态',
primary key (`id`)
);

create table match(
`id` int(11) AUTO_INCREMENT,
`game_id` int,


);

-- 服务器转发的消息
create table `msg_relay` (
`id` int(11) not null auto_increment,
`msg_id` varchar(20) comment '消息 id',
`from_id` int(8) comment '发送人',
`to_id` int(8) comment '接收的赛事',
`user_id` int(8) comment '消息所属人 id',
`update_date` datetime comment '最后修改时间',

constraint pk_tab primary key (`id`)
);

-- 服务器接收的消息
CREATE TABLE `msg_receive` (
`id` int(11) not null auto_increment,
`msg_id` varchar(20) comment '消息 id',
`from_id` int(8) comment '发送人',
`to_id` int(8) comment '接收的赛事',
`user_id` int(8) comment '消息所属人 id',
`update_date` datetime comment '最后修改时间',

constraint pk_tab primary key (`id`)
);


insert  user(username,password,name,birthday,sex,address) values('aa','aa','aa',now(),'a','aa');

insert into temp_tab(code,name,update_date) values('ccc','nnn',now());
insert into temp_tab(code,name,update_date) values('cc','nn',DATE_FORMAT('2018-02-12 13:23:02','%Y-%m-%d %T'));


select * from user;
select * from temp_tab;